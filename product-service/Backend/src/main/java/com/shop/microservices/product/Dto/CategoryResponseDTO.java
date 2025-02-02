package com.shop.microservices.product.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

/**
 * Represents the response object for a Category in the shop's catalog.
 * <p>
 * This DTO is used to transfer Category data from the backend to the client.
 * It includes the following fields:
 * </p>
 * <ul>
 *     <li><b>id:</b> Unique identifier for the Category (UUID).</li>
 *     <li><b>name:</b> The name of the Category (String).</li>
 *     <li><b>description:</b> A detailed description of the Category (String).</li>
 * </ul>
 */
@ApiModel(description = "Represents the response object for a Category in the shop's catalog.")
public record CategoryResponseDTO(

        @ApiModelProperty(notes = "Unique identifier of the Category", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @ApiModelProperty(notes = "The name of the Category", required = true, example = "T-Shirt")
        String name,

        @ApiModelProperty(notes = "The description of the Category", required = true, example = "T-Shirt for Men - Large")
        String description
) {}
