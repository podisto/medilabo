package com.medilabo.resource;

import com.medilabo.persistence.Note;
import com.medilabo.persistence.NoteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return ResponseEntity.ok(optional.orElse(null));
    }

    @PostMapping
    public ResponseEntity<Note> addNoteForPatient(@RequestBody Note newNote) {
        log.info("add notes to patient {}", newNote.getPatId());
        Optional<Note> optional = noteRepo.findById(newNote.getPatId());
        if (optional.isEmpty()) {
            log.info("will add the first note for patient {}", newNote.getPatient());
            Note created = noteRepo.save(newNote);
            log.info("first note created");
            return ResponseEntity.ok(created);
        }
        log.info("will add new note to existing one");
        Note found = optional.get();
        found.getNotes().add(newNote.getNotes().get(0));
        Note updated = noteRepo.save(found);
        return ResponseEntity.ok(updated);
    }
}
