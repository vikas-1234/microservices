package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("USER-SERVICE", r -> r.path("/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("HOTEL-SERVICE-STAFFS", r -> r.path("/staffs/**")
                        .uri("lb://HOTEL-SERVICE"))
                .route("HOTEL-SERVICE-HOTELS", r -> r.path("/hotels/**")
                        .uri("lb://HOTEL-SERVICE"))
                .route("RATING-SERVICE", r -> r.path("/ratings/**")
                        .uri("lb://RATING-SERVICE"))
                .build();
    }
}
