package com.medilabo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PatientProperties {
    @Value("${list-patient-uri}")
    private String listPatientUri;
}
