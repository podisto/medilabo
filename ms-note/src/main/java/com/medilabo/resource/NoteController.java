package com.medilabo.resource;

import com.medilabo.persistence.Note;
import com.medilabo.persistence.NoteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("notes")
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
}
