package com.shop.microservices.product.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

/**
 * Represents the response object for a material in the shop's catalog.
 * <p>
 * This DTO is used to transfer material data from the backend to the client.
 * It includes the following fields:
 * </p>
 * <ul>
 *     <li><b>materialId:</b> Unique identifier for the material (UUID).</li>
 *     <li><b>materialName:</b> The name of the material (String).</li>
 *     <li><b>materialType:</b> The type of the material (String).</li>
 *     <li><b>description:</b> A detailed description of the material (String).</li>
 * </ul>
 */
@ApiModel(description = "Represents the response object for a material in the shop's catalog.")
public record MaterialResponseDTO(

        @ApiModelProperty(notes = "Unique identifier for the material", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
        UUID materialId,

        @ApiModelProperty(notes = "The name of the material", required = true, example = "Cotton")
        String materialName,

        @ApiModelProperty(notes = "The type of the material", required = true, example = "Fabric")
        String materialType,

        @ApiModelProperty(notes = "A detailed description of the material", required = false, example = "100% organic cotton, ideal for T-shirts and summer dresses.")
        String description
) {}
