package com.medilabo.patient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRouter {

    @Value("${patient-uri}")
    private String patientUri;


    @Bean
    public RouteLocator patientRouter(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/").filters(f -> f.rewritePath("/.*", "/patient?page=0&size=20")).uri(patientUri))
                .route(r -> r.path("/patient").filters(f -> f.rewritePath("/.*", "/patient?page=0&size=20")).uri(patientUri))
                .route(r -> r.path("/patient/{id}").filters(f -> f.rewritePath("/patient/(?<id>.*)", "/patient/${id}")).uri(patientUri))
                .build();
    }
}
