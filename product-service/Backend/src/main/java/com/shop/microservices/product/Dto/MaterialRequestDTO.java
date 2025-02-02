package com.shop.microservices.product.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a Material in the system.
 * <p>
 * This DTO contains the necessary information for a Material request, such as the material name, material type,
 * and an optional description.
 * Validation annotations ensure that the incoming data meets business requirements.
 * </p>
 */
@Getter
@Setter
@ApiModel(description = "Request DTO for creating or updating a Material in the clothing shop.")
public class MaterialRequestDTO {

    /**
     * The name of the Material.
     * The name must not be blank and is required for creating or updating a Material.
     * <p>
     * -- GETTER -- <br>
     * Retrieves the name of the Material.
     * </p>
     * <p>
     * -- SETTER -- <br>
     * Assigns a name to the Material.
     * </p>
     */
    @NotBlank(message = "Material name must not be blank")
    @ApiModelProperty(value = "The name of the Material", required = true, example = "Cotton")
    private String materialName;

    /**
     * The type of the Material.
     * The type must not be blank and is required for creating or updating a Material.
     * <p>
     * -- GETTER -- <br>
     * Retrieves the type of the Material.
     * </p>
     * <p>
     * -- SETTER -- <br>
     * Assigns a type to the Material.
     * </p>
     */
    @NotBlank(message = "Material type must not be blank")
    @ApiModelProperty(value = "The type of the Material", required = true, example = "Fabric")
    private String materialType;

    /**
     * A detailed description of the Material.
     * The description is optional but should provide additional context or details about the Material.
     * <p>
     * -- GETTER -- <br>
     * Retrieves the description of the Material.
     * </p>
     * <p>
     * -- SETTER -- <br>
     * Assigns a description to the Material.
     * </p>
     */
    @ApiModelProperty(value = "The description of the Material", required = false, example = "100% organic cotton suitable for T-shirts and summer dresses.")
    private String description;
}
