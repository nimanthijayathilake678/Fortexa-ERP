package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Security configuration for the API Gateway.
 *
 * This class configures security settings, including CORS, CSRF, OAuth2 resource server,
 * and role-based authorization based on a 'primary_role' JWT claim.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String[] PUBLIC_URLS = {
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/refresh-token",
            "/actuator/**"
    };

    // Claim name for the primary role in the JWT
    private static final String PRIMARY_ROLE_CLAIM = "primary_role";

    // Converter to extract authorities from JWT, focusing on 'primary_role'
    // Changed from private to package-private (default) to allow access from test class
    Converter<Jwt, Mono<AbstractAuthenticationToken>> customJwtAuthenticationConverter() {
        // This standard converter handles "scope" or "scp" claims.
        JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        return jwt -> {
            // Get default authorities (e.g., from 'scope' claim)
            Collection<GrantedAuthority> defaultAuthorities = defaultGrantedAuthoritiesConverter.convert(jwt);
            if (defaultAuthorities.isEmpty()) {
                defaultAuthorities = new ArrayList<>(); // Initialize if null
            } else {
                defaultAuthorities = new ArrayList<>(defaultAuthorities); // Make mutable to add more
            }

            String primaryRole = jwt.getClaimAsString(PRIMARY_ROLE_CLAIM);
            if (primaryRole != null && !primaryRole.trim().isEmpty()) {
                // Add the primary role, prefixed with "ROLE_"
                defaultAuthorities.add(new SimpleGrantedAuthority("ROLE_" + primaryRole.toUpperCase()));
                logger.debug("Extracted primary role: ROLE_{}", primaryRole.toUpperCase());
            } else {
                logger.debug("No '{}' claim found in JWT or it's empty.", PRIMARY_ROLE_CLAIM);
            }

            // Use a Set to combine and avoid duplicates if any
            Collection<GrantedAuthority> finalAuthorities = new HashSet<>(defaultAuthorities);

            // Last argument to JwtAuthenticationToken is the principal name. Using jwt.getSubject() is common.
            return Mono.just(new JwtAuthenticationToken(jwt, finalAuthorities, jwt.getSubject()));
        };
    }

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
                        // Sample role-based authorization rules:
                        .pathMatchers("/api/admin-console/**").hasRole("ADMIN")
                        .pathMatchers("/api/management/reports/**").hasAnyRole("MANAGER", "OWNER")
                        .pathMatchers(HttpMethod.GET, "/api/products/**").hasRole("USER")
                        .pathMatchers(HttpMethod.POST, "/api/products/**").hasRole("USER")
                        .pathMatchers(HttpMethod.PUT, "/api/products/**").hasRole("USER")
                        .pathMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("USER")
                        .pathMatchers("/api/v1/users/**").hasRole("USER")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter()))
                );

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        logger.info("Security filter chain configured with custom JWT converter for 'primary_role' and role-based authorization.");
        return http.build();
    }

    /**
     * Configures CORS settings for the API Gateway.
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
     * @return a list of allowed origins
     */
    private List<String> getAllowedOrigins() {
        return Collections.singletonList("http://localhost:3000");
    }

    /**
     * Defines a custom global filter for the API Gateway.
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