package com.medilabo.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.persistence.Note;
import com.medilabo.persistence.NoteRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepo noteRepo;

    @GetMapping
    public ResponseEntity<List<Note>> listNotes() {
        log.info("fetch all notes");
        final List<Note> notes = noteRepo.findAll();
        log.info("{} notes fetched", notes.size());
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> noteForPatient(@PathVariable("id") String patId) {
        log.info("get notes for patient ID {}", patId);
        final Optional<Note> optional = noteRepo.findById(patId);
        if (optional.isEmpty()) {
            log.info("No notes for patient with ID {}", patId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody NoteRequest noteRequest) {
        log.info("attempt to add note for Patient ID {}, name {}", noteRequest.getPatId(), noteRequest.getPatient());
        final Note note = Note.builder().patId(noteRequest.getPatId()).patient(noteRequest.getPatient())
                .notes(noteRequest.getNotes()).build();
        final Note created = noteRepo.save(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Note> addNoteForPatient(@RequestParam("patId") String patId, @RequestBody String note) {
        log.info("add notes to patient {}", patId);
        final Optional<Note> optional = noteRepo.findById(patId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        final Note foundNote = optional.get();
        foundNote.getNotes().add(note);
        final Note updated = noteRepo.save(foundNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }
}
