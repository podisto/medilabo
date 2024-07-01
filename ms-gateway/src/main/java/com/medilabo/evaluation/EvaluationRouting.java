package com.medilabo.evaluation;

import com.medilabo.auth.GatewayAuthFilter;
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
    public RouteLocator evaluationRouter(RouteLocatorBuilder builder, GatewayAuthFilter gatewayFilter) {
        return builder
                .routes()
                .route(r -> r.path("/evaluation").filters(f -> f.rewritePath("/.*", "/evaluation").filter(gatewayFilter)).uri(evaluationUri))
                .route(r -> r.path("/evaluation/{id}").filters(f -> f.rewritePath("/evaluation/(?<id>.*)", "/evaluation/${id}").filter(gatewayFilter)).uri(evaluationUri))
                .build();
    }
}
