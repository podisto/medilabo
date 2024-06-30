package com.medilabo;

import com.medilabo.model.EntityModelPatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/patient/dossier")
    public String dossierPatient(Model model) {
        log.info("display dossier patient");
        return "dossier_patient";
    }
}
