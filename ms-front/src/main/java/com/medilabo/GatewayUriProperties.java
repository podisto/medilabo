package com.medilabo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GatewayUriProperties {
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
}
