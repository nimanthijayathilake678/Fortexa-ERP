package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway configuration for defining routing rules.
 */
@Configuration
public class GatewayConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    private static final int STRIP_PREFIX_COUNT = 2;

    /**
     * Define routing rules using RouteLocatorBuilder.
     * Routes are configured for user-service, auth-service, and product-service.
     *
     * @param builder the {@link RouteLocatorBuilder} instance
     * @return the configured {@link RouteLocator}
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        logger.info("Initializing Gateway routes...");

        return builder.routes()
                // Route for user-service
                .route("user-service-route", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT))
                        .uri("lb://user-service")
                )

                // Route for auth-service
                .route("auth-service-route", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT))
                        .uri("lb://auth-service")
                )

                // Route for product-service
                .route("product-service-route", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT))
                        .uri("lb://product-service")
                )

                .build();
    }
}