package com.shop.microservices.product.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents the response object for a product in the shop's catalog.
 * <p>
 * This DTO is used to transfer product data from the backend to the client.
 * It includes the following fields:
 * </p>
 * <ul>
 *     <li><b>id:</b> Unique identifier for the product (UUID).</li>
 *     <li><b>name:</b> The name of the product (String).</li>
 *     <li><b>description:</b> A detailed description of the product (String).</li>
 *     <li><b>price:</b> The price of the product (BigDecimal).</li>
 * </ul>
 */
@ApiModel(description = "Represents the response object for a product in the shop's catalog.")
public record ProductResponseDTO(

        @ApiModelProperty(notes = "Unique identifier for the product", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @ApiModelProperty(notes = "The name of the product", required = true, example = "Wireless Mouse")
        String name,

        @ApiModelProperty(notes = "A detailed description of the product", required = false, example = "A high-precision wireless mouse with ergonomic design.")
        String description,

        @ApiModelProperty(notes = "The price of the product", required = true, example = "29.99")
        BigDecimal price
) {}
