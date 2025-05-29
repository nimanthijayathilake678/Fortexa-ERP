package com.fortexa.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

/**
 * Configuration class that checks and logs Keycloak and Redis setup details at startup.
 * Helps ensure necessary properties are correctly set.
 */
@Configuration
public class StartupConfig {

    private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}")
    private String keycloakIssuerUri;

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private String redisPort;

    /**
     * Logs the current issuer URI and Redis settings at application startup.
     * Issues warnings if required properties are missing or use defaults.
     */
    @PostConstruct
    public void logConfiguration() {
        logger.info("--- Configuration Check ---");

        // Check Keycloak Issuer URI
        if (keycloakIssuerUri == null || keycloakIssuerUri.isEmpty()) {
            logger.warn("Keycloak Issuer URI (spring.security.oauth2.resourceserver.jwt.issuer-uri) is NOT configured. This is critical for security.");
        } else if ("http://localhost:8080/realms/your-realm".equals(keycloakIssuerUri)
                || "http://keycloak:8080/realms/your-realm".equals(keycloakIssuerUri)) {
            logger.warn("Keycloak Issuer URI is using a default/placeholder value: {}. Please ensure it's correctly set for your environment.", keycloakIssuerUri);
        } else {
            logger.info("Keycloak Issuer URI: {}", keycloakIssuerUri);
        }

        // Check Redis Host
        if ("localhost".equals(redisHost)) {
            logger.warn("Redis Host (spring.data.redis.host) is configured to default 'localhost': {}. This might be intentional for local dev, but verify for other environments.", redisHost);
        } else if (redisHost == null || redisHost.isEmpty()) {
            logger.warn("Redis Host (spring.data.redis.host) is NOT configured. This is needed for rate limiting.");
        } else {
            logger.info("Redis Host: {}", redisHost);
        }

        // Log Redis Port
        logger.info("Redis Port: {}", redisPort);

        logger.info("--- Configuration Check End ---");
    }
}