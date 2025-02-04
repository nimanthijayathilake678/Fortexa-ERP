package com.shop.microservices.user_service.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

/**
 * Configuration class to enable JPA auditing in the application.
 * <p>
 * This class is annotated with {@link Configuration} to indicate that it is a source of bean definitions.
 * The {@link EnableJpaAuditing} annotation is used to enable JPA auditing and specify the {@link AuditorAware} bean.
 * </p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    /**
     * Provides an implementation of {@link AuditorAware} to be used for auditing purposes.
     * <p>
     * This bean returns an instance of {@link AuditorAwareImpl}, which retrieves the current auditor (user)
     * from the Spring Security context.
     * </p>
     *
     * @return an instance of {@link AuditorAwareImpl}
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}