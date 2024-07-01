package com.medilabo;

import lombok.Data;

@Data
public class EvaluationRisqueResponse {
    private String patId;
    private String expectedRisque;

    public EvaluationRisqueResponse(String patId, String expectedRisque) {
        this.patId = patId;
        this.expectedRisque = expectedRisque;
    }
}
