package com.shop.microservices.user_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating personal information in the system.
 * This DTO contains the necessary information for a personal information request, such as first name, middle name, last name, NIC, picture ID, and address.
 * Validation annotations ensure that the incoming data meets business requirements.
 */
@Getter
@Setter
@Schema(description = "Request DTO for creating or updating personal information.")
public class PersonalInfoRequestDTO {

    /**
     * The first name of the person.
     * This field must not be blank and is required for creating or updating personal information.
     * -- GETTER --
     *  Gets the first name of the person.
     *
     * -- SETTER --
     *  Sets the first name of the person.
     */
    @NotBlank(message = "First name must not be blank")
    @Schema(description = "The first name of the person",  example = "John")
    private String firstName;

    /**
     * The middle name of the person.
     * This field is optional and can be null.
     * -- GETTER --
     *  Gets the middle name of the person.
     *
     * -- SETTER --
     *  Sets the middle name of the person.
     */
    @Schema(description = "The middle name of the person", example = "Michael")
    private String middleName;

    /**
     * The last name of the person.
     * This field must not be blank and is required for creating or updating personal information.
     * -- GETTER --
     *  Gets the last name of the person.
     *
     * -- SETTER --
     *  Sets the last name of the person.
     */
    @NotBlank(message = "Last name must not be blank")
    @Schema(description = "The last name of the person", example = "Doe")
    private String lastName;

    /**
     * The National Identity Card (NIC) number of the person.
     * This field must not be blank and is required for creating or updating personal information.
     * -- GETTER --
     *  Gets the NIC of the person.
     *
     * -- SETTER --
     *  Sets the NIC of the person.
     */
    @NotBlank(message = "NIC must not be blank")
    @Schema(description = "The National Identity Card (NIC) number of the person", example = "123456789V")
    private String nic;

    /**
     * The ID of the user's profile picture.
     * This field is optional and can be null if the user has no profile picture.
     * -- GETTER --
     *  Gets the picture ID of the person.
     *
     * -- SETTER --
     *  Sets the picture ID of the person.
     */
    @Schema(description = "The ID of the user's profile picture", example = "1")
    private int pictureId;

    /**
     * The address of the person.
     * This field is optional and can be left null if not provided.
     * -- GETTER --
     *  Gets the address of the person.
     *
     * -- SETTER --
     *  Sets the address of the person.
     */
    @Schema(description = "The address of the person", example = "123 Main St, Springfield, USA")
    private String address;
}