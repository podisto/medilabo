package com.medilabo;

import com.medilabo.model.EntityModelPatient;
import com.medilabo.model.PagedModelEntityModelPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PatientService {

    private final RestTemplate authRestTemplate;
    private final PatientProperties properties;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, PatientProperties properties) {
        this.authRestTemplate = authRestTemplate;
        this.properties = properties;
    }

    public List<EntityModelPatient> getListPatients() {
        log.info("attempt to fetch list of patients");
        PagedModelEntityModelPatient pagedModelPatient = authRestTemplate.getForObject(properties.getListPatientUri(), PagedModelEntityModelPatient.class);
        return (pagedModelPatient == null || pagedModelPatient.getEmbedded() == null) ? new ArrayList<>() : pagedModelPatient.getEmbedded().getPatient();
    }

    public EntityModelPatient findById(Long id) {
        String uri = String.format(properties.getDossierPatientUri(), id);
        return authRestTemplate.getForObject(uri, EntityModelPatient.class);
    }
}
