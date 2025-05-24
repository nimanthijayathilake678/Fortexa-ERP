package com.fortexa.api_gateway.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SecurityConfigTest {

    private SecurityConfig securityConfig;
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(); // Instantiate directly
        jwtAuthenticationConverter = securityConfig.jwtAuthenticationConverter();
    }

    private Jwt mockJwt(Map<String, Object> claims) {
        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getClaims()).thenReturn(claims);
        when(jwt.getTokenValue()).thenReturn("test-token");
        when(jwt.getIssuedAt()).thenReturn(Instant.now());
        when(jwt.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));
        when(jwt.getSubject()).thenReturn("test-subject");
        // Add other necessary mocks if the converter uses them, e.g., getSubject() for logging
        return jwt;
    }

    @Test
    void correctRoleExtraction() {
        Map<String, Object> realmAccess = Map.of("roles", List.of("user", "product_admin"));
        Jwt jwt = mockJwt(Map.of("realm_access", realmAccess));

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PRODUCT_ADMIN")));
    }

    @Test
    void jwtWithNoRealmAccessClaim() {
        Jwt jwt = mockJwt(Collections.emptyMap()); // No realm_access claim

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        assertTrue(token.getAuthorities().isEmpty());
    }

    @Test
    void jwtWithRealmAccessButNoRolesList() {
        Map<String, Object> realmAccess = Collections.emptyMap(); // realm_access is empty
        Jwt jwt = mockJwt(Map.of("realm_access", realmAccess));

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        assertTrue(token.getAuthorities().isEmpty());
    }
    
    @Test
    void jwtWithRealmAccessButRolesIsNotCollection() {
        Map<String, Object> realmAccess = Map.of("roles", "not-a-collection"); // roles is not a collection
        Jwt jwt = mockJwt(Map.of("realm_access", realmAccess));

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        assertTrue(token.getAuthorities().isEmpty());
    }

    @Test
    void jwtWithEmptyRolesList() {
        Map<String, Object> realmAccess = Map.of("roles", Collections.emptyList());
        Jwt jwt = mockJwt(Map.of("realm_access", realmAccess));

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        assertTrue(token.getAuthorities().isEmpty());
    }

    @Test
    void roleNamesWithMixedCaseShouldBeUppercased() {
        Map<String, Object> realmAccess = Map.of("roles", List.of("UsEr", "Product_Admin"));
        Jwt jwt = mockJwt(Map.of("realm_access", realmAccess));

        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt).block();
        assertNotNull(token);
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertEquals(2, authorities.size());
        // Roles are converted to ROLE_PREFIX + UPPERCASE
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PRODUCT_ADMIN")));
    }
}
