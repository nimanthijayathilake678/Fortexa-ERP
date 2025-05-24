package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class StartupConfig {

    private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}") // Default to empty string if not set
    private String keycloakIssuerUri;

    @Value("${spring.data.redis.host:localhost}") // Default to localhost if not set
    private String redisHost;

    @Value("${spring.data.redis.port:6379}") // Default to 6379 if not set
    private String redisPort;

    @PostConstruct
    public void logConfiguration() {
        logger.info("--- Configuration Check ---");

        // Check Keycloak Issuer URI
        if (keycloakIssuerUri == null || keycloakIssuerUri.isEmpty()) {
            logger.warn("Keycloak Issuer URI (spring.security.oauth2.resourceserver.jwt.issuer-uri) is NOT configured. This is critical for security.");
        } else if ("http://localhost:8080/realms/your-realm".equals(keycloakIssuerUri) || "http://keycloak:8080/realms/your-realm".equals(keycloakIssuerUri)) {
            logger.warn("Keycloak Issuer URI is using a default/placeholder value: {}. Please ensure it's correctly set for your environment.", keycloakIssuerUri);
        } else {
            logger.info("Keycloak Issuer URI: {}", keycloakIssuerUri);
        }

        // Check Redis Host
        if ("localhost".equals(redisHost)) {
            logger.warn("Redis Host (spring.data.redis.host) is configured to default 'localhost': {}. This might be intentional for local dev, but verify for other environments.", redisHost);
        } else if (redisHost == null || redisHost.isEmpty()) {
            logger.warn("Redis Host (spring.data.redis.host) is NOT configured. This is needed for rate limiting.");
        }
        else {
            logger.info("Redis Host: {}", redisHost);
        }

        // Log Redis Port (default is usually fine, but good to know)
        logger.info("Redis Port: {}", redisPort);

        logger.info("--- Configuration Check End ---");
    }
}
