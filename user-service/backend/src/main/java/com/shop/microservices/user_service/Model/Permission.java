package com.shop.microservices.user_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Represents a permission entity in the system.
 * A permission defines a specific action that a user or role can perform within the application.
 * This class extends {@link Auditable} to inherit creation and modification tracking features.
 *
 * The entity is used to manage roles and permissions, and ensures each permission is unique
 * within the system. Auditing annotations automatically handle the population of created/modified
 * metadata.
 *
 * <p>
 * A {@link Permission} can be associated with multiple {@link Role}s, facilitating the management
 * of user capabilities through role-based access control.
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends Auditable {

    /**
     * Unique identifier for the permission. Generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The permission name or code. This field must be unique across the system.
     * It represents a specific action that can be assigned to roles or users.
     */
    @Column(unique = true, nullable = false)
    private String permission;

    /**
     * A detailed description of the permission. This can provide further context
     * for understanding the specific use case of the permission.
     */
    private String description;

    /**
     * The set of {@link Role}s that are associated with this permission.
     * A permission can be linked to multiple roles, granting users in those roles
     * the associated permission.
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
