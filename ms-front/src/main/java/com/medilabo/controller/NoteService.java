package com.medilabo.controller;

import com.medilabo.config.GatewayProperties;
import com.medilabo.dto.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NoteService {

    private final RestTemplate restTemplate;
    private final GatewayProperties properties;

    public NoteService(@Qualifier("authRestTemplate") RestTemplate restTemplate, GatewayProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public void createNote(Note note) {
        log.info("creating note {}", note);
        Note created = restTemplate.postForObject(properties.getCreateNoteUri(), note, Note.class);
        log.info("note added {}", created.getNotes());
    }
}
