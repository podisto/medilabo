package com.medilabo.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
@Slf4j
public class GatewayAuthFilter implements GatewayFilter {

    public static final List<String> openApiEndpoints = List.of("/auth/");

    public static final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth-token-validation-uri}")
    private String authValidationUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing auth header");
            }

            String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            try {
                boolean isValid = restTemplate.getForObject(String.format(authValidationUri, authHeader), Boolean.class);
                log.info("TOKEN VALID ?: {}", isValid);
            } catch (Exception e) {
                log.info("ERROR: {}", e.getMessage());
                throw new RuntimeException("ERROR: {} " + e.getMessage());
            }
        }
        return chain.filter(exchange);
    }

}
