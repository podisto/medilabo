package com.medilabo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.dto.PatientDetails;
import com.medilabo.model.EntityModelPatient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping({ "/", "/index", "/patient" })
    public String showPatientList(Model model) {
        log.info("display list patients");
        final List<EntityModelPatient> patients = patientService.getListPatients();
        log.info("{} patients retrieved", patients.size());
        model.addAttribute("patients", patients);
        return "index";
    }

    @GetMapping("/patient/{id}/dossier")
    public String dossierPatient(@PathVariable("id") Long id, Model model) {
        try {
            log.info("display dossier for patient ID {}", id);
            final PatientDetails patient = patientService.getDossierPatient(id);
            model.addAttribute("patient", patient);
            return "dossier_patient";
        } catch (final Exception e) {
            return "error";
        }
    }

    @GetMapping("/patient/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        log.info("display edit form for patient ID {}", id);
        return "edit_patient";
    }
}
