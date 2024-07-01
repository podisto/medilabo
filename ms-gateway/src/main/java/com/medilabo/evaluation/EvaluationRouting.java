package com.medilabo.evaluation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medilabo.auth.GatewayAuthFilter;

@Configuration
public class EvaluationRouting {

    @Value("${evaluation-uri}")
    private String evaluationUri;

    @Bean
    public RouteLocator evaluationRouter(RouteLocatorBuilder builder, GatewayAuthFilter gatewayAuthFilter) {
        return builder.routes()
                .route(r -> r.path("/evaluation")
                        .filters(f -> f.rewritePath("/.*", "/evaluation").filter(gatewayAuthFilter)).uri(evaluationUri))
                .route(r -> r.path("/evaluation/{id}").filters(
                        f -> f.rewritePath("/evaluation/(?<id>.*)", "/evaluation/${id}").filter(gatewayAuthFilter))
                        .uri(evaluationUri))
                .build();
    }
}
