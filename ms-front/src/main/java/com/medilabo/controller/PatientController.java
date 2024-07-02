package com.medilabo.controller;

import com.medilabo.dto.PatientDetails;
import com.medilabo.services.PatientService;
import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PatientRequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping({"/", "/index", "/patient"})
    public String showPatientList(Model model) {
        log.info("display list patients");
        List<EntityModelPatient> patients = patientService.getListPatients();
        log.info("{} patients retrieved", patients.size());
        model.addAttribute("patients", patients);
        return "index";
    }

    @GetMapping("/patient/{id}/dossier")
    public String dossierPatient(@PathVariable("id") Long id, Model model) {
        log.info("display dossier for patient ID {}", id);
        final PatientDetails patient = patientService.getDossierPatient(id);
        model.addAttribute("patient", patient);
        return "dossier_patient";
    }

    @GetMapping("/patient/create")
    public String showCreationForm(Model model) {
        log.info("display creation form for patient");
        final PatientRequestBody patientRequestBody = new PatientRequestBody();
        model.addAttribute("patient", patientRequestBody);
        return "creer_patient";
    }

    @PostMapping("/patient/create")
    public String createPatient(@Valid PatientRequestBody patientRequestBody, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "creer_patient";
        }
        patientService.createPatient(patientRequestBody);
        return "redirect:/index";
    }

    @GetMapping("/patient/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            log.info("display edit form for patient ID {}", id);
            final PatientDetails patient = patientService.getDossierPatient(id);
            model.addAttribute("patient", patient);
            return "edit_patient";
        } catch (final Exception e) {
            return "error";
        }
    }

    @PostMapping("/patient/{id}/update")
    public String updatePatient(@PathVariable("id") long id, @Valid PatientRequestBody patientRequestBody,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit_patient";
        }
        patientService.updatePatient(patientRequestBody);
        return "redirect:/index";
    }
}
