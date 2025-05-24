package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Security configuration for the API Gateway.
 *
 * This class configures security settings, including CORS, CSRF, and OAuth2 resource server.
 * It also defines a global filter for request and response processing.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String[] PUBLIC_URLS = {
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/refresh-token",
            "/actuator/health/**"
    };

    /**
     * Configures the security filter chain for the API Gateway.
     *
     * @param http the {@link ServerHttpSecurity} instance
     * @return the configured {@link SecurityWebFilterChain}
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PUBLIC_URLS).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        logger.info("Security filter chain configured successfully.");
        return http.build();
    }

    /**
     * Configures CORS settings for the API Gateway.
     *
     * @return the {@link CorsWebFilter} instance
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(getAllowedOrigins());
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        logger.debug("CORS configuration applied with allowed origins: {}", getAllowedOrigins());
        return new CorsWebFilter(source);
    }

    /**
     * Retrieves the list of allowed origins for CORS.
     *
     * @return a list of allowed origins
     */
    private List<String> getAllowedOrigins() {
        // Load allowed origins from configuration or environment variables
        return Collections.singletonList("http://localhost:3000");
    }

    /**
     * Defines a custom global filter for the API Gateway.
     *
     * This filter adds custom headers to requests and responses for tracking purposes.
     *
     * @return the {@link GlobalFilter} instance
     */
    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            logger.info("Processing request: {}", exchange.getRequest().getURI());

            exchange.getRequest().mutate().header("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis())).build();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                exchange.getResponse().getHeaders().add("X-Gateway-Processed", "true");
                logger.info("Response processed for: {}", exchange.getRequest().getURI());
            }));
        };
    }
}