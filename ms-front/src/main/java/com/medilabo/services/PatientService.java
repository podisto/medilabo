package com.medilabo.services;

import com.medilabo.config.GatewayProperties;
import com.medilabo.exception.MedilaboException;
import com.medilabo.dto.Note;
import com.medilabo.dto.EvaluationRisque;
import com.medilabo.dto.PatientDetails;
import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;
import com.medilabo.model.PatientRequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PatientService {

    private final RestTemplate authRestTemplate;
    private final GatewayProperties properties;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties properties) {
        this.authRestTemplate = authRestTemplate;
        this.properties = properties;
    }

    public List<EntityModelPatient> getListPatients() {
        log.info("attempt to fetch list of patients");
        PagedModelEntityModelPatient pagedModelPatient = authRestTemplate.getForObject(properties.getListPatientUri(), PagedModelEntityModelPatient.class);
        return (pagedModelPatient == null || pagedModelPatient.getEmbedded() == null) ? new ArrayList<>() : pagedModelPatient.getEmbedded().getPatient();
    }

    public PatientDetails getDossierPatient(Long id) {
        final EntityModelPatient patient = authRestTemplate.getForObject(String.format(properties.getDetailPatientUri(), id), EntityModelPatient.class);
        if (patient == null) {
            throw new MedilaboException("Patient is null");
        }
        Note notePatient = authRestTemplate.getForObject(String.format(properties.getNotePatientUri(), id), Note.class);
        List<String> notes = notePatient == null ? new ArrayList<>() : new ArrayList<>(notePatient.getNotes());

        EvaluationRisque evaluationRisque = authRestTemplate.getForObject(String.format(properties.getEvaluationRisqueUri(), id), EvaluationRisque.class);
        return PatientDetails
                .builder()
                .id(patient.getId())
                .lastName(patient.getLastName())
                .firstName(patient.getFirstName())
                .birthDate(patient.getBirthDate())
                .gender(patient.getGender().getValue())
                .address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber())
                .notes(notes)
                .evaluationRisque(evaluationRisque == null ? null : evaluationRisque.getExpectedRisque())
                .build();
    }

    public void createPatient(PatientRequestBody patientRequestBody) {
        log.info("creating patient {} {}", patientRequestBody.getFirstName(), patientRequestBody.getLastName());
        EntityModelPatient created = authRestTemplate.postForObject(properties.getCreatePatientUri(), patientRequestBody, EntityModelPatient.class);
        assert created != null;
        log.info("patient created with ID {}", created.getId());
    }

    public void updatePatient(PatientRequestBody patientRequestBody) {
        log.info("updating patient {}: {} {}", patientRequestBody.getId(), patientRequestBody.getFirstName(), patientRequestBody.getLastName());
        final String updatePatientUri = String.format(properties.getUpdatePatientUri(), patientRequestBody.getId());
        authRestTemplate.put(updatePatientUri, patientRequestBody, EntityModelPatient.class);
        log.info("patient updated");
    }
}
