package com.shop.microservices.user_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents personal information of a user.
 * This entity is mapped to the 'personal_info' table in the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {

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
     * This field is optional.
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
     * This field is optional.
     */
    private int pictureId;

    /**
     * Address of the user.
     * This field is optional.
     */
    private String address;
}
