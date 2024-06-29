package com.medilabo;

import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final RestTemplate restTemplate;
    private final PatientProperties properties;

    public List<EntityModelPatient> getListPatients() {
        log.info("attempt to fetch list of patients");
        PagedModelEntityModelPatient pagedModelPatient = restTemplate.getForObject(properties.getListPatientUri(), PagedModelEntityModelPatient.class);
        return (pagedModelPatient == null || pagedModelPatient.getEmbedded() == null) ? new ArrayList<>() : pagedModelPatient.getEmbedded().getPatient();
    }
}
