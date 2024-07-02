package com.medilabo.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRouting {

    @Value("${auth-uri}")
    private String authUri;

    @Bean
    public RouteLocator authRouterLocator(RouteLocatorBuilder builder, GatewayAuthFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/auth").filters(f -> f.filter(filter)).uri(authUri))
                .route(r -> r.path("/auth/validate").filters(f -> f.filter(filter)).uri(authUri))
                .build();
    }


}
