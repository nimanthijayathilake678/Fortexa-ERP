package com.shop.microservices.user_service.Dto;

import com.shop.microservices.user_service.Enumeration.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for responding with User information.
 * This DTO contains the necessary information for a User response, such as username, email, mobile number, status, and two-factor authentication.
 */
@Builder
@Schema(description = "Response DTO for User information.")
public record UserResponseDTO(
        @Schema(description = "The unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "The username of the user", example = "john_doe")
        String username,

        @Schema(description = "The email address of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "The mobile number of the user", example = "+1234567890")
        String mobileNo,

        @Schema(description = "The status of the user", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE"}, enumAsRef = true, defaultValue = "ACTIVE")
        UserStatusEnum status,

        @Schema(description = "Indicates whether two-factor authentication is enabled for the user", allowableValues = {"true", "false"}, defaultValue = "false", example = "false")
        boolean twoFactorEnabled,

        @Schema(description = "The date and time when the user was created", example = "2023-01-01T12:00:00")
        LocalDateTime createdDate,

        @Schema(description = "The date and time when the user was last modified", example = "2023-01-02T12:00:00")
        LocalDateTime lastModifiedDate,

        @Schema(description = "The user who created the user", example = "admin")
        String createdBy,

        @Schema(description = "The user who last modified the user", example = "admin")
        String lastModifiedBy
) {
}