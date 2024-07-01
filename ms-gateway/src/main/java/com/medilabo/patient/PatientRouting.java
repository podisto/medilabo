package com.medilabo.patient;

import com.medilabo.auth.GatewayAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRouting {

    @Value("${patient-uri}")
    private String patientUri;

    @Bean
    public RouteLocator patientRouter(RouteLocatorBuilder builder, GatewayAuthFilter authFilter) {
        return builder
                .routes()
                .route(r -> r.path("/").filters(f -> f.rewritePath("/.*", "/patient?page=0&size=20").filter(authFilter)).uri(patientUri))
                .route(r -> r.path("/patient").filters(f -> f.rewritePath("/.*", "/patient?page=0&size=20").filter(authFilter)).uri(patientUri))
                .route(r -> r.path("/patient/{id}").filters(f -> f.rewritePath("/patient/(?<id>.*)", "/patient/${id}").filter(authFilter)).uri(patientUri))
                .build();
    }
}
