package com.shop.microservices.product.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a Category in the system.
 * This DTO contains the necessary information for a Category request, such as the name and description
 * Validation annotations ensure that the incoming data meets business requirements.
 */
@Getter
@Setter
@ApiModel(description = "Request DTO for creating or updating a Category.")
public class CategoryRequestDTO {

    /**
     * The name of the Category.
     * The name must not be blank and is required for creating or updating a Category.
     * -- GETTER --
     *  Gets the name of the Category.
     *
     * -- SETTER --
     *  Sets the name of the Category.
     *
     */
    @NotBlank(message = "Category name must not be blank")
    @ApiModelProperty(value = "The name of the Category", required = true, example = "T-Shirt")
    private String name;

    /**
     * A detailed description of the Category.
     * The description is optional but should be a string.
     * -- GETTER --
     *  Gets the description of the Category.
     *
     * -- SETTER --
     *  Sets the description of the Category.
     *
     */
    @ApiModelProperty(value = "The Description of the Category", required = false, example = "T-Shirt for Men - Large")
    private String description;
}
