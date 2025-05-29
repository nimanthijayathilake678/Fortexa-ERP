package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining API Gateway routes and applying rate limiting.
 * This class sets up routing rules for various microservices and integrates
 * Redis-based rate limiting using a custom KeyResolver.
 */
@Configuration
public class GatewayConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);
    private static final int STRIP_PREFIX_COUNT = 2;

    private final KeyResolver customUserKeyResolver;

    /**
     * Constructor for injecting the custom KeyResolver.
     *
     * @param customUserKeyResolver the KeyResolver used for rate limiting
     */
    public GatewayConfig(KeyResolver customUserKeyResolver) {
        this.customUserKeyResolver = customUserKeyResolver;
    }

    /**
     * Configures the routing rules for the API Gateway.
     * Routes are defined for user-service, auth-service, and product-service,
     * with rate limiting applied using the custom KeyResolver.
     *
     * @param builder the RouteLocatorBuilder instance
     * @return the configured RouteLocator
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        logger.info("Initializing Gateway routes with rate limiting...");

        return builder.routes()
                .route("user-service-route", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT)
                                .requestRateLimiter(config -> config.setKeyResolver(this.customUserKeyResolver))
                        )
                        .uri("lb://user-service")
                )
                .route("auth-service-route", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT)
                                .requestRateLimiter(config -> config.setKeyResolver(this.customUserKeyResolver))
                        )
                        .uri("lb://auth-service")
                )
                .route("product-service-route", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.stripPrefix(STRIP_PREFIX_COUNT)
                                .requestRateLimiter(config -> config.setKeyResolver(this.customUserKeyResolver))
                        )
                        .uri("lb://product-service")
                )
                .build();
    }
}