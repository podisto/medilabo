package com.medilabo.resource;

import com.medilabo.persistence.Note;
import com.medilabo.persistence.NoteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepo noteRepo;

    @GetMapping
    public ResponseEntity<List<Note>> listNotes() {
        log.info("fetch all notes");
        List<Note> notes = noteRepo.findAll();
        log.info("{} notes fetched", notes.size());
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> noteForPatient(@PathVariable("id") String patId) {
        log.info("get notes for patient ID {}", patId);
        Optional<Note> optional = noteRepo.findById(patId);
        if (optional.isEmpty()) {
            log.info("No notes for patient with ID {}", patId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody NoteRequest noteRequest) {
        log.info("attempt to add note for Patient ID {}, name {}", noteRequest.getPatId(), noteRequest.getPatient());
        Note note = Note.builder()
                .patId(noteRequest.getPatId())
                .patient(noteRequest.getPatient())
                .notes(noteRequest.getNotes())
                .build();
        Note created = noteRepo.save(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/patient")
    public ResponseEntity<Note> addNoteForPatient(@RequestParam("patId") String patId, @RequestBody String note) {
        log.info("add notes to patient {}", patId);
        Optional<Note> optional = noteRepo.findById(patId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Note foundNote = optional.get();
        foundNote.getNotes().add(note);
        Note updated = noteRepo.save(foundNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }
}
