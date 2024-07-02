package com.medilabo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RestTemplateConfig {

    @Bean("restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestScope
    @Bean("authRestTemplate")
    RestTemplate authRestTemplate(TokenContextHolder contextHolder) {
        System.out.println("### CONTEXT HOLDER " +contextHolder.getToken());
        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + contextHolder.getToken());
            return execution.execute(request, body);
        })).build();
    }
}
