package com.medilabo.evaluation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluationRouting {


    @Value("${evaluation-uri}")
    private String evaluationUri;


    @Bean
    public RouteLocator evaluationRouter(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/evaluation").filters(f -> f.rewritePath("/.*", "/evaluation")).uri(evaluationUri))
                .route(r -> r.path("/evaluation/{id}").filters(f -> f.rewritePath("/evaluation/(?<id>.*)", "/evaluation/${id}")).uri(evaluationUri))
                .build();
    }
}
