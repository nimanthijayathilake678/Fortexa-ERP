package com.shop.microservices.user_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a Permission in the system.
 * This DTO contains the necessary information for a Permission request, such as permission name and description.
 * Validation annotations ensure that the incoming data meets business requirements.
 */
@Getter
@Setter
@Schema(description = "Request DTO for creating or updating a Permission.")
public class PermissionRequestDTO {

    /**
     * The permission name or code.
     * This field must not be blank and is required for creating or updating a Permission.
     * -- GETTER --
     *  Gets the permission name or code.
     *
     * -- SETTER --
     *  Sets the permission name or code.
     */
    @NotBlank(message = "Permission must not be blank")
    @Schema(description = "The permission name or code", example = "READ_USER")
    private String permission;

    /**
     * A detailed description of the permission.
     * This field is optional but provides further context for understanding the specific use case of the permission.
     * -- GETTER --
     *  Gets the description of the permission.
     *
     * -- SETTER --
     *  Sets the description of the permission.
     */
    @Schema(description = "A detailed description of the permission", example = "Allows reading user information")
    private String description;
}