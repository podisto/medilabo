package com.medilabo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.GatewayUriProperties;
import com.medilabo.dto.Note;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final RestTemplate restTemplate;

    private final GatewayUriProperties properties;

    public void createNote(Note noteRequestBody) {
        restTemplate.postForObject(properties.getCreateNoteUri(), noteRequestBody, String.class);
    }

    public void addNote(Long id, String note) {
        final String updateNoteUri = String.format(properties.getNotePatientUri(), id);
        restTemplate.postForObject(updateNoteUri, note, String.class);
    }
}
