package com.fortexa.api_gateway;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;


/**
 * Integration tests for Keycloak JWT based authorization.
 * These tests mock the JWT decoding process to simulate various authentication
 * and authorization scenarios.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test") // Ensure test profile is active if needed for specific configurations
public class KeycloakAuthorizationIntegrationTests {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakAuthorizationIntegrationTests.class);

    @Autowired
    private WebTestClient webTestClient;

    // --- Test Endpoints ---
    private static final String PROTECTED_USER_ENDPOINT = "/api/v1/users/profile"; // Example user-specific endpoint
    private static final String PROTECTED_ADMIN_ENDPOINT = "/api/products/admin-summary"; // Example admin-specific endpoint
    private static final String PUBLIC_ENDPOINT = "/actuator/health"; // Publicly accessible

    /**
     * Creates a mock JWT token string.
     * For actual Spring Security Test `mockJwt()` usage, we'd apply it to the WebTestClient exchange.
     * This helper is more for conceptualizing what a token might look like or for a custom mock ReactiveJwtDecoder.
     */
    private Jwt createMockJwt(String subject, List<String> roles) {
        Map<String, Object> realmAccess = roles != null ? Map.of("roles", roles) : Collections.emptyMap();
        return Jwt.withTokenValue("mock-token-" + UUID.randomUUID())
                .header("alg", "RS256")
                .claim("sub", subject)
                .claim("realm_access", realmAccess)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();
    }

    /**
     * Test accessing a protected endpoint without any authentication.
     * Expected: 401 Unauthorized.
     */
    @Test
    void unauthenticatedAccessToProtectedEndpoint() {
        logger.info("Test: Unauthenticated access to {}", PROTECTED_USER_ENDPOINT);
        webTestClient.get().uri(PROTECTED_USER_ENDPOINT)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    /**
     * Test accessing a user-protected endpoint with a JWT that has authentication
     * but lacks the required 'ROLE_USER'.
     * Expected: 403 Forbidden.
     */
    @Test
    void authenticatedAccessWithInsufficientRoleToUserEndpoint() {
        logger.info("Test: Authenticated access with insufficient role to {}", PROTECTED_USER_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().authorities(() -> new SimpleGrantedAuthority("ROLE_SOME_OTHER_ROLE"))) // Using spring-security-test
                .get().uri(PROTECTED_USER_ENDPOINT)
                .exchange()
                .expectStatus().isForbidden();
    }
    
    /**
     * Test accessing an admin-protected endpoint with a JWT that only has 'ROLE_USER'.
     * Expected: 403 Forbidden.
     */
    @Test
    void authenticatedAccessWithInsufficientRoleToAdminEndpoint() {
        logger.info("Test: Authenticated access with insufficient role (ROLE_USER) to {}", PROTECTED_ADMIN_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().authorities(() -> new SimpleGrantedAuthority("ROLE_USER")))
                .get().uri(PROTECTED_ADMIN_ENDPOINT)
                .exchange()
                .expectStatus().isForbidden();
    }


    /**
     * Test accessing a user-protected endpoint with a JWT that has the required 'ROLE_USER'.
     * Expected: A non-401/403 status. Since the downstream service might not be running
     * or might not have the specific resource, we typically expect 404 (Not Found from downstream)
     * or 503 (Service Unavailable if user-service is not registered/found by gateway).
     * The key is that the gateway's security filter permitted the request.
     */
    @Test
    void authenticatedAccessWithSufficientRoleToUserEndpoint() {
        logger.info("Test: Authenticated access with sufficient role (ROLE_USER) to {}", PROTECTED_USER_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().authorities(() -> new SimpleGrantedAuthority("ROLE_USER")))
                .get().uri(PROTECTED_USER_ENDPOINT)
                .exchange()
                .expectStatus().value(status -> 
                    assertTrue(status != HttpStatus.UNAUTHORIZED.value() && status != HttpStatus.FORBIDDEN.value(),
                            "Expected non-401/403 status, but got " + status)
                );
        // Note: For a more robust test, if a downstream mock server (e.g., WireMock) was set up,
        // you could expect a 200 OK from that mock.
    }
    
    /**
     * Test accessing an admin-protected endpoint with a JWT that has the required 'ROLE_PRODUCT_ADMIN'.
     * Expected: A non-401/403 status.
     */
    @Test
    void authenticatedAccessWithSufficientRoleToAdminEndpoint() {
        logger.info("Test: Authenticated access with sufficient role (ROLE_PRODUCT_ADMIN) to {}", PROTECTED_ADMIN_ENDPOINT);
        webTestClient
                .mutateWith(mockJwt().authorities(() -> new SimpleGrantedAuthority("ROLE_PRODUCT_ADMIN")))
                .get().uri(PROTECTED_ADMIN_ENDPOINT)
                .exchange()
                .expectStatus().value(status -> 
                    assertTrue(status != HttpStatus.UNAUTHORIZED.value() && status != HttpStatus.FORBIDDEN.value(),
                            "Expected non-401/403 status, but got " + status)
                );
    }

    /**
     * Test accessing a public endpoint that requires no authentication.
     * Expected: 200 OK.
     */
    @Test
    void accessToPublicEndpoint() {
        logger.info("Test: Access to public endpoint {}", PUBLIC_ENDPOINT);
        webTestClient.get().uri(PUBLIC_ENDPOINT)
                .exchange()
                .expectStatus().isOk();
    }

    /**
     * Optional: Example of using a TestConfiguration to provide a mock ReactiveJwtDecoder.
     * This is an alternative to `mockJwt()` if more control over the Jwt object itself is needed
     * or if `spring-security-test` is not available/behaving as expected.
     * If this is used, you would typically not use `.mutateWith(mockJwt())` in the tests above,
     * but rather send a bearer token header and this decoder would interpret it.
     */
    @TestConfiguration
    static class MockJwtDecoderConfig {

        // @Bean
        // public ReactiveJwtDecoder reactiveJwtDecoder() {
        //     return token -> {
        //         // This is a very basic mock decoder.
        //         // In a real scenario, you'd inspect the 'token' string if you were sending one,
        //         // or just return a predefined Jwt for all calls if you control the client calls precisely.
        //         logger.warn("Using MOCK ReactiveJwtDecoder in TestConfiguration. All tokens will be decoded to a default mock JWT.");
        //         
        //         // Example: Default mock JWT with ROLE_USER for any token string passed
        //         // This would mean all "authenticated" tests pass if they hit this decoder.
        //         // You'd need more sophisticated logic here to vary roles based on the token string
        //         // or have multiple decoders/test setups.
        //         Map<String, Object> realmAccess = Map.of("roles", List.of("user"));
        //         Jwt mockJwt = Jwt.withTokenValue(token) // use the passed token string
        //                 .header("alg", "none")
        //                 .claim("sub", "test-user-from-mock-decoder")
        //                 .claim("realm_access", realmAccess)
        //                 .issuedAt(Instant.now())
        //                 .expiresAt(Instant.now().plusSeconds(300))
        //                 .build();
        //         return Mono.just(mockJwt);
        //     };
        // }
    }
}
