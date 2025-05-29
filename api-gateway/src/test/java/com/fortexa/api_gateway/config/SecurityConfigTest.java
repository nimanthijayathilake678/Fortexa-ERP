package com.fortexa.api_gateway.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SecurityConfig, focusing on the custom JWT converter logic.
 */
class SecurityConfigTest {

    private SecurityConfig securityConfig;
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter;
    private static final String PRIMARY_ROLE_CLAIM = "primary_role";

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
        jwtAuthenticationConverter = securityConfig.customJwtAuthenticationConverter();
    }

    private Jwt buildJwt(String subject, String primaryRole, String scpClaim) {
        Map<String, Object> claims = new HashMap<>();
        if (primaryRole != null) {
            claims.put(PRIMARY_ROLE_CLAIM, primaryRole);
        }
        if (scpClaim != null && !scpClaim.isBlank()) {
            claims.put("scp", scpClaim);
        }
        return Jwt.withTokenValue("test-token-" + UUID.randomUUID())
                .header("alg", "none")
                .subject(subject != null ? subject : "test-subject")
                .claims(allClaims -> {
                    allClaims.putAll(claims);
                    allClaims.putIfAbsent("iat", Instant.now());
                    allClaims.putIfAbsent("exp", Instant.now().plusSeconds(3600));
                    allClaims.putIfAbsent("iss", "test-issuer");
                })
                .build();
    }

    private Jwt buildJwt(String primaryRole, String scpClaim) {
        return buildJwt("test-subject", primaryRole, scpClaim);
    }

    @Test
    void jwtWithPrimaryRole_shouldCreateRoleAuthority() {
        Jwt jwt = buildJwt("admin", null);
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void jwtWithMixedCasePrimaryRole_shouldCreateUppercaseRoleAuthority() {
        Jwt jwt = buildJwt("AdMiN", null);
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void jwtWithoutPrimaryRole_shouldNotCreateRoleAuthority() {
        Jwt jwt = buildJwt(null, "read");
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertTrue(authorities.stream().noneMatch(auth -> auth.getAuthority().startsWith("ROLE_")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_read")));
    }

    @Test
    void jwtWithEmptyPrimaryRole_shouldNotCreateRoleAuthority() {
        Jwt jwt = buildJwt("", "read");
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertTrue(authorities.stream().noneMatch(auth -> auth.getAuthority().startsWith("ROLE_")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_read")));
    }

    @Test
    void jwtWithScopeClaims_shouldCreateScopeAuthorities() {
        Jwt jwt = buildJwt(null, "read write");
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_read")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_write")));
    }

    @Test
    void jwtWithBothPrimaryRoleAndScopeClaims_shouldCreateAllAuthorities() {
        Jwt jwt = buildJwt("manager", "create delete");
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertEquals(3, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_MANAGER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_create")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("SCOPE_delete")));
    }

    @Test
    void jwtWithNoPrimaryRoleAndNoScopeClaims_shouldHaveNoAuthorities() {
        Jwt jwt = buildJwt(null, null);
        AbstractAuthenticationToken authenticationToken = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(authenticationToken);
        assertTrue(authenticationToken.getAuthorities().isEmpty());
    }
}