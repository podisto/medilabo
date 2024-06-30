package com.medilabo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PatientProperties {
    @Value("${list-patient-uri}")
    private String listPatientUri;

    @Value("${detail-patient-uri}")
    private String detailPatientUri;
}
