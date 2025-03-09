package com.shop.microservices.user_service.Dto;

import com.shop.microservices.user_service.Enumeration.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for responding with User-Role information.
 * This DTO contains the necessary information for a User-Role response, such as ID, user ID, role, and audit fields.
 */
@Schema(description = "Response DTO for User-Role information.")
public record UserRoleResponseDTO(
        @Schema(description = "The unique identifier of the User-Role association", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "The ID of the User", example = "123e4567-e89b-12d3-a456-426614174000")
        String userId,

        @Schema(description = "The role included in the RoleEnum", example = "ADMIN", allowableValues = {"ADMIN", "OWNER",
                "SUPER_USER", "GENERAL_MANAGER", "PRODUCT_MANAGER", "INVENTORY_MANAGER", "MARKETING_MANAGER", "SALES_MANAGER",
                "ACCOUNTANT", "CASHIER", "LOGISTIC_STAFF", "DATA_ANALYST", "WAREHOUSE_STAFF", "HR_MANAGER", "QA"}, enumAsRef = true)
        RoleEnum role,

        @Schema(description = "The date and time when the User-Role association was created", example = "2023-01-01T12:00:00")
        LocalDateTime createdDate,

        @Schema(description = "The date and time when the User-Role association was last modified", example = "2023-01-02T12:00:00")
        LocalDateTime lastModifiedDate,

        @Schema(description = "The user who created the User-Role association", example = "admin")
        String createdBy,

        @Schema(description = "The user who last modified the User-Role association", example = "admin")
        String lastModifiedBy
) {
}