package com.medilabo.controller;

import com.medilabo.dto.Note;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{patId}/{patient}")
    public String displayNoteForm(@PathVariable("patId") Long patId, @PathVariable("patient") String patient, Model model) {
        log.info("add new note for patient ID: {} {}", patId, patient);
        final Note note = new Note();
        note.setPatId(String.valueOf(patId));
        note.setPatient(patient);
        note.setNotes(new ArrayList<>());
        model.addAttribute("noteForm", note);
        return "creer_note";
    }

    @PostMapping
    public String handleNoteForm(@Valid Note note, BindingResult result) {
        if (result.hasErrors()) {
            return "creer_note";
        }
        noteService.createNote(note);
        return "redirect:/patient/"+note.getPatId()+"/dossier";
    }
}
