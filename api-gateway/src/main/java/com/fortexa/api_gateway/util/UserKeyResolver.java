package com.fortexa.api_gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Configuration class for resolving user keys for rate limiting.
 *
 * This class provides a custom implementation of the {@link KeyResolver} interface
 * to generate unique keys for each user based on their authentication details.
 */
@Configuration
public class UserKeyResolver {

    private static final Logger logger = LoggerFactory.getLogger(UserKeyResolver.class);

    private static final String DEFAULT_KEY = "anonymous";

    /**
     * Defines a custom KeyResolver bean for rate limiting.
     *
     * This resolver extracts the authenticated user's name as the key. If the user
     * is not authenticated, it defaults to the key "anonymous".
     *
     * @return a {@link KeyResolver} instance
     */
    @Bean
    public KeyResolver customUserKeyResolver() {
        return exchange -> exchange.getPrincipal()
                .map(principal -> {
                    String key = principal.getName();
                    logger.debug("Resolved user key: {}", key);
                    return key;
                })
                .switchIfEmpty(Mono.just(DEFAULT_KEY)
                        .doOnNext(key -> logger.warn("No authenticated user found. Using default key: {}", key)));
    }
}