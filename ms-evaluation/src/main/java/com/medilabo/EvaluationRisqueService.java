package com.medilabo;

import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.medilabo.Risque.*;
import static java.time.LocalDate.now;

// parmi les recommandations green code, l'appel vers le ms patient n'est pas tres utile vu que dans le ms note
// donc dans la BD mongo on a deja l'info sur le patient. Eliminer cet appel = optimiser
@Service
@Slf4j
@RequiredArgsConstructor
public class EvaluationRisqueService {

    private final List<String> triggers = List.of("Hémoglobine A1C", "Microalbumine", "Taille",
            "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges",
            "Rechute", "Réaction", "Anticorps");

    private final RestTemplate restTemplate;
    @Value("${patient-detail-uri}")
    private String patientUri;
    @Value("${list-patient-uri}")
    private String listPatientUri;
    @Value("${note-detail-uri}")
    private String noteUri;

    public EvaluationRisqueResponse getEvaluationRisqueForPatient(String patId) {
        EntityModelPatient patient = restTemplate.getForObject(String.format(patientUri, Long.valueOf(patId)), EntityModelPatient.class);
        if (patient == null) {
            log.info("no patient with ID " + patId + " found");
            throw new IllegalArgumentException("Patient with ID " + patId + " not found");
        }
        Note note = restTemplate.getForObject(String.format(noteUri, patient.getId()), Note.class);
        log.info("note for patient {}", note);
        return evaluate(patient, note);
    }

    public List<EvaluationRisqueResponse> getEvaluationRisqueForAllPatients() {
        PagedModelEntityModelPatient pagedModel = restTemplate.getForObject(listPatientUri, PagedModelEntityModelPatient.class);
        List<EntityModelPatient> patients = (pagedModel == null || pagedModel.getEmbedded() == null) ? new ArrayList<>() : pagedModel.getEmbedded().getPatient();
        if (patients.isEmpty()) {
            log.info("no patients found");
            throw new IllegalArgumentException("no patients found");
        }
        log.info("{} patients retrieved", patients.size());
        return patients.stream().map(patient -> getEvaluationRisqueForPatient(String.valueOf(patient.getId()))).toList();
    }

    private EvaluationRisqueResponse evaluate(EntityModelPatient patient, Note note) {
        String patId = String.valueOf(patient.getId());
        if (note == null || note.getNotes().isEmpty()) {
            log.info("None no note on patient case");
            return new EvaluationRisqueResponse(patId, NONE);
        }

        int triggerOccurrence = getTriggerOccurrences(note);
        if (triggerOccurrence == 0) {
            log.info("None");
            return new EvaluationRisqueResponse(patId, NONE);
        }

        if ((triggerOccurrence >= 2 && triggerOccurrence <= 5) && age(patient) > 30) {
            log.info("Borderline");
            return new EvaluationRisqueResponse(patId, BORDERLINE);
        }

        if ((isMale(patient) && age(patient) < 30 && triggerOccurrence == 3)
                || (isFemale(patient) && age(patient) < 30 && triggerOccurrence == 4)
                || (age(patient) > 30 && (triggerOccurrence == 6 || triggerOccurrence == 7))) {
            log.info("In Danger");
            return new EvaluationRisqueResponse(patId, INDANGER);
        }

        if ((isMale(patient) && age(patient) < 30 && triggerOccurrence >= 5)
                || (isFemale(patient) && age(patient) < 30 && triggerOccurrence >= 7)
                || (age(patient) > 30 && triggerOccurrence >= 8)) {
            log.info("EarlyOnset");
            return new EvaluationRisqueResponse(patId, EARLYONSET);
        }

        return new EvaluationRisqueResponse(patId, NONE);
    }

    private boolean isMale(EntityModelPatient patient) {
        return patient.getGender() == EntityModelPatient.GenderEnum.M;
    }

    private boolean isFemale(EntityModelPatient patient) {
        return patient.getGender() == EntityModelPatient.GenderEnum.F;
    }

    private int age(EntityModelPatient patient) {
        return Period.between(patient.getBirthDate(), now()).getYears();
    }

    private int getTriggerOccurrences(Note note) {
        List<String> notes = note.getNotes();
        List<String> occurrences = new ArrayList<>();
        for (String item : notes) {
            for (String term : triggers) {
                if (item.toLowerCase().contains(term.toLowerCase())) {
                    occurrences.add(term);
                }
            }
        }

        log.info("list of matching occurrences found {}", occurrences);
        return occurrences.size();
    }

}
