package com.medilabo;

import com.medilabo.persistence.Patient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class MsPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPatientApplication.class, args);
    }

    @Bean
    RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> config.exposeIdsFor(Patient.class));
    }

}
