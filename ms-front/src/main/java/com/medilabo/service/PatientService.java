package com.medilabo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.GatewayUriProperties;
import com.medilabo.dto.Note;
import com.medilabo.dto.PatientDetails;
import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;
import com.medilabo.model.PatientRequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final RestTemplate restTemplate;

    private final GatewayUriProperties properties;

    public List<EntityModelPatient> getListPatients() {
        log.info("attempt to fetch list of patients");
        final PagedModelEntityModelPatient pagedModelPatient = restTemplate.getForObject(properties.getListPatientUri(),
                PagedModelEntityModelPatient.class);
        return (pagedModelPatient == null || pagedModelPatient.getEmbedded() == null) ? new ArrayList<>()
                : pagedModelPatient.getEmbedded().getPatient();
    }

    public PatientDetails getDossierPatient(Long id) {
        final String patientUri = String.format(properties.getDetailPatientUri(), id);
        final String noteUri = String.format(properties.getNotePatientUri(), id);
        final EntityModelPatient patient = restTemplate.getForObject(patientUri, EntityModelPatient.class);
        if (patient == null) {
            throw new RuntimeException("Patient is null");
        }
        List<String> notes;
        try {
            final Note note = restTemplate.getForObject(noteUri, Note.class);
            if (note == null) {
                throw new RuntimeException("Note is null");
            }
            notes = note.getNotes();
        } catch (final Exception e) {
            notes = new ArrayList<>();
        }
        return PatientDetails.builder().id(patient.getId()).lastName(patient.getLastName())
                .firstName(patient.getFirstName()).birthDate(patient.getBirthDate())
                .gender(patient.getGender().getValue()).address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber()).notes(notes).build();
    }

    public void createPatient(PatientRequestBody patientRequestBody) {
        restTemplate.postForObject(properties.getCreatePatientUri(), patientRequestBody, String.class);
    }

    public void updatePatient(PatientRequestBody patientRequestBody) {
        final String updatePatientUri = String.format(properties.getUpdatePatientUri(), patientRequestBody.getId());
        restTemplate.put(updatePatientUri, patientRequestBody, String.class);
    }
}
