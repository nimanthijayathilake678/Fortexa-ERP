package com.fortexa.api_gateway;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

/**
 * Integration tests for Keycloak authorization and role-based access control.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class KeycloakAuthorizationIntegrationTests {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakAuthorizationIntegrationTests.class);

    @Autowired
    private WebTestClient webTestClient;

    private static final String PROTECTED_ADMIN_ENDPOINT = "/api/admin-console/some-admin-action";
    private static final String PROTECTED_MANAGER_ENDPOINT = "/api/management/reports/monthly";
    private static final String PUBLIC_ENDPOINT = "/actuator/health";

    @Test
    void unauthenticatedAccessToProtectedAdminEndpoint() {
        logger.info("Test: Unauthenticated access to {}", PROTECTED_ADMIN_ENDPOINT);
        webTestClient.get().uri(PROTECTED_ADMIN_ENDPOINT)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void authenticatedAccessWithUserRoleToAdminEndpoint() {
        logger.info("Test: Authenticated access with USER role to {}", PROTECTED_ADMIN_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("primary_role", "USER")))
                .get().uri(PROTECTED_ADMIN_ENDPOINT)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void authenticatedAccessWithSufficientAdminRoleToAdminEndpoint() {
        logger.info("Test: Authenticated access with ADMIN role to {}", PROTECTED_ADMIN_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("primary_role", "ADMIN")))
                .get().uri(PROTECTED_ADMIN_ENDPOINT)
                .exchange()
                .expectStatus().value(status ->
                        assertTrue(
                                status != HttpStatus.UNAUTHORIZED.value() && status != HttpStatus.FORBIDDEN.value(),
                                "Expected non-401/403 for ADMIN on admin endpoint, but got " + status
                        )
                );
    }

    @Test
    void authenticatedAccessWithManagerRoleToManagerEndpoint() {
        logger.info("Test: Authenticated access with MANAGER role to {}", PROTECTED_MANAGER_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("primary_role", "MANAGER")))
                .get().uri(PROTECTED_MANAGER_ENDPOINT)
                .exchange()
                .expectStatus().value(status ->
                        assertTrue(
                                status != HttpStatus.UNAUTHORIZED.value() && status != HttpStatus.FORBIDDEN.value(),
                                "Expected non-401/403 for MANAGER on manager endpoint, but got " + status
                        )
                );
    }

    @Test
    void authenticatedAccessWithOwnerRoleToManagerEndpoint() {
        logger.info("Test: Authenticated access with OWNER role to {}", PROTECTED_MANAGER_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("primary_role", "OWNER")))
                .get().uri(PROTECTED_MANAGER_ENDPOINT)
                .exchange()
                .expectStatus().value(status ->
                        assertTrue(
                                status != HttpStatus.UNAUTHORIZED.value() && status != HttpStatus.FORBIDDEN.value(),
                                "Expected non-401/403 for OWNER on manager endpoint, but got " + status
                        )
                );
    }

    @Test
    void authenticatedAccessWithAdminRoleToManagerEndpoint() {
        logger.info("Test: Authenticated access with ADMIN role to {}", PROTECTED_MANAGER_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("primary_role", "ADMIN")))
                .get().uri(PROTECTED_MANAGER_ENDPOINT)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void accessToPublicEndpoint() {
        logger.info("Test: Access to public endpoint {}", PUBLIC_ENDPOINT);
        webTestClient.get().uri(PUBLIC_ENDPOINT)
                .exchange()
                .expectStatus().isOk();
    }

    @TestConfiguration
    static class MockJwtDecoderConfig {
        @Bean
        public ReactiveJwtDecoder reactiveJwtDecoder() {
            return token -> {
                Map<String, Object> claims = new HashMap<>();
                claims.put("sub", "test-subject");
                if ("admin-token".equals(token)) {
                    claims.put("primary_role", "ADMIN");
                } else if ("user-token".equals(token)) {
                    claims.put("primary_role", "USER");
                    claims.put("scp", "read write");
                }
                Jwt mockJwt = Jwt.withTokenValue(token)
                        .header("alg", "none")
                        .claims(c -> c.putAll(claims))
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plusSeconds(3600))
                        .build();
                return Mono.just(mockJwt);
            };
        }
    }
}