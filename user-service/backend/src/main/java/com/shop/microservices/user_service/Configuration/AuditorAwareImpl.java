package com.shop.microservices.user_service.Configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of AuditorAware to provide the current auditor (user) for JPA auditing.
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor (user) based on the Spring Security context.
     *
     * @return an Optional containing the username of the authenticated user, or an empty Optional if no user is authenticated
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If there is no authentication or the user is not authenticated, return an empty Optional
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // Return the username of the authenticated user
        return Optional.of(authentication.getName());
    }
}