package com.medilabo.auth;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GatewayAuthFilter implements GatewayFilter {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth-token-validation-uri}")
    private String authValidationUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getURI().getPath().contains("/auth")) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing auth header");
            }

            String authHeader = Objects
                    .requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            try {
                final boolean isValid = restTemplate.getForObject(String.format(authValidationUri, authHeader),
                        Boolean.class);
                log.info("TOKEN VALID ?: {}", isValid);
            } catch (final Exception e) {
                log.info("ERROR: {}", e.getMessage());
                throw new RuntimeException("ERROR: {} " + e.getMessage());
            }
        }
        return chain.filter(exchange);
    }
}
