package com.shop.microservices.user_service.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents personal information associated with a user.
 * This entity is mapped to the 'personal_info' table in the database.
 * <p>
 * The PersonalInfo entity contains essential details such as the user's name, NIC,
 * and optional fields such as address and profile picture ID. It extends the {@link Auditable}
 * class to track auditing fields like createdBy, createdDate, etc.
 * </p>
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo extends Auditable {

    /**
     * Unique identifier for the personal information entry.
     * Generated using UUID strategy as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * First name of the user.
     * This field is required and cannot be null.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Middle name of the user.
     * This field is optional and can be null.
     */
    private String middleName;

    /**
     * Last name of the user.
     * This field is required and cannot be null.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * National Identity Card (NIC) number of the user.
     * This field is unique and required.
     */
    @Column(unique = true, nullable = false)
    private String nic;

    /**
     * ID of the user's profile picture.
     * This field is optional and can be null if the user has no profile picture.
     */
    private int pictureId;

    /**
     * Address of the user.
     * This field is optional and can be left null if not provided.
     */
    private String address;
}
