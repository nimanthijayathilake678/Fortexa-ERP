package com.shop.microservices.user_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for responding with personal information.
 * This DTO contains the necessary information for a personal information response, such as first name, middle name, last name, NIC, picture ID, and address.
 */
@Schema(description = "Response DTO for personal information.")
public record PersonalInfoResponseDTO(
        @Schema(description = "The unique identifier of the person", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "The first name of the person", example = "John")
        String firstName,

        @Schema(description = "The middle name of the person", example = "Michael")
        String middleName,

        @Schema(description = "The last name of the person", example = "Doe")
        String lastName,

        @Schema(description = "The National Identity Card (NIC) number of the person", example = "123456789V")
        String nic,

        @Schema(description = "The ID of the user's profile picture", example = "1")
        int pictureId,

        @Schema(description = "The address of the person", example = "123 Main St, Springfield, USA")
        String address,

        @Schema(description = "The date and time when the personal information was created", example = "2023-01-01T12:00:00")
        LocalDateTime createdDate,

        @Schema(description = "The date and time when the personal information was last modified", example = "2023-01-02T12:00:00")
        LocalDateTime lastModifiedDate,

        @Schema(description = "The user who created the personal information", example = "admin")
        String createdBy,

        @Schema(description = "The user who last modified the personal information", example = "admin")
        String lastModifiedBy
) {
}