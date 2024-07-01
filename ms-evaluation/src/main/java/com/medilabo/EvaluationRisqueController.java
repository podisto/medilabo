package com.medilabo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
@Slf4j
public class EvaluationRisqueController {

    private final EvaluationRisqueService evaluationRisqueService;

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationRisqueResponse> getEvaluationForPatient(@PathVariable("id") String patId) {
        log.info("get evaluation risque for patient ID {}", patId);
        try {
            EvaluationRisqueResponse evaluationRisque = evaluationRisqueService.getEvaluationRisqueForPatient(patId);
            return ResponseEntity.ok(evaluationRisque);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EvaluationRisqueResponse>> getEvaluationForAllPatients() {
        log.info("get evaluation risque for all patients");
        try {
            List<EvaluationRisqueResponse> listEvaluation = evaluationRisqueService.getEvaluationRisqueForAllPatients();
            return ResponseEntity.ok(listEvaluation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
