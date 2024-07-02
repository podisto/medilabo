package com.medilabo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GatewayProperties {
    @Value("${default-patient-uri}")
    private String listPatientUri;

    @Value("${default-patient-uri}")
    private String createPatientUri;

    @Value("${patient-id-uri}")
    private String detailPatientUri;

    @Value("${patient-id-uri}")
    private String updatePatientUri;

    @Value("${note-patient-uri}")
    private String notePatientUri;

    @Value("${create-note-uri}")
    private String createNoteUri;

    @Value("${auth-uri}")
    private String authUri;

    @Value("${evaluation-risque-uri}")
    private String evaluationRisqueUri;
}
