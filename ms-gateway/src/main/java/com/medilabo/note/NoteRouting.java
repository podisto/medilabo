package com.medilabo.note;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoteRouting {

    @Value("${note-uri}")
    private String noteUri;


    @Bean
    public RouteLocator noteRouter(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/note").filters(f -> f.rewritePath("/.*", "/note")).uri(noteUri))
                .route(r -> r.path("/note/{id}").filters(f -> f.rewritePath("/note/(?<id>.*)", "/note/${id}")).uri(noteUri))
                .build();
    }
}

