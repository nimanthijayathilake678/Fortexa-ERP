package com.shop.microservices.user_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Base entity class that provides auditing fields for creation and modification metadata.
 * Automatically populated by Spring Data JPA's {@link AuditingEntityListener}.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    /**
     * The user who created the entity. Populated automatically on entity creation.
     * Set by Spring Security or the application context.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * The timestamp when the entity was created. Populated automatically on entity creation.
     */
    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;

    /**
     * The user who last modified the entity. Populated automatically on entity update.
     * Set by Spring Security or the application context.
     */
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * The timestamp when the entity was last modified. Populated automatically on entity update.
     */
    @LastModifiedDate
    private Instant lastModifiedDate;
}
