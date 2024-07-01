package com.medilabo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.dto.Note;
import com.medilabo.service.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/patient/{patId}/{patient}/first_note")
    public String showCreationForm(@PathVariable("patId") Long id, @PathVariable("patient") String lastName,
            Model model) {
        log.info("display creation form for notes");
        final Note note = new Note();
        note.setPatId(id.toString());
        note.setPatient(lastName);
        model.addAttribute("note", note);
        return "creer_note";
    }

    @PostMapping("/patient/{patId}/{patient}/first_note")
    public String createNote(@Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "creer_note";
        }
        noteService.createNote(note);
        return "redirect:/index";
    }

    @GetMapping("/patient/{patId}/{patient}/new_note")
    public String showAdditionForm(@PathVariable("patId") Long id, @PathVariable("patient") String lastName,
            Model model) {
        log.info("display creation form for notes");
        final Note note = new Note();
        note.setPatId(id.toString());
        note.setPatient(lastName);
        model.addAttribute("note", note);
        return "ajouter_note";
    }

    @PostMapping("/patient/{patId}/new_note")
    public String addNote(@PathVariable("id") Long id, @Valid String note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ajouter_note";
        }
        noteService.addNote(id, note);
        return "redirect:/index";
    }
}
