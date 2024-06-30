package com.medilabo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.dto.Note;
import com.medilabo.dto.PatientDetails;
import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final RestTemplate restTemplate;

    private final PatientProperties properties;

    public List<EntityModelPatient> getListPatients() {
        log.info("attempt to fetch list of patients");
        final PagedModelEntityModelPatient pagedModelPatient = restTemplate.getForObject(properties.getListPatientUri(),
                PagedModelEntityModelPatient.class);
        return (pagedModelPatient == null || pagedModelPatient.getEmbedded() == null) ? new ArrayList<>()
                : pagedModelPatient.getEmbedded().getPatient();
    }

    public PatientDetails getDossierPatient(Long id) {
        final String uri = String.format(properties.getDetailPatientUri(), id);
        final EntityModelPatient patient = restTemplate.getForObject(uri, EntityModelPatient.class);
        final Note note = restTemplate.getForObject(uri, Note.class);
        if (patient == null || note == null) {
            throw new RuntimeException("Either patient or note is null");
        }
        return PatientDetails.builder().id(patient.getId()).lastName(patient.getLastName())
                .firstName(patient.getFirstName()).birthDate(patient.getBirthDate())
                .gender(patient.getGender().getValue()).address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber()).notes(note.getNotes()).build();
    }
}
