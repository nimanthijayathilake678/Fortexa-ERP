package com.shop.microservices.user_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Set;

/**
 * Represents a permission entity in the system. A permission defines a specific
 * action that a user or a role can perform. This entity tracks creation and
 * modification details using auditing annotations.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    /**
     * Unique identifier for the permission.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The permission name or code, which must be unique.
     */
    @Column(unique = true, nullable = false)
    private String permission;

    /**
     * A description providing more details about the permission.
     */
    private String description;

    /**
     * Roles associated with this permission.
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    /**
     * The username of the person who created this permission.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * The timestamp when this permission was created.
     */
    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;

    /**
     * The username of the person who last modified this permission.
     */
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * The timestamp when this permission was last modified.
     */
    @LastModifiedDate
    private Instant lastModifiedDate;
}
