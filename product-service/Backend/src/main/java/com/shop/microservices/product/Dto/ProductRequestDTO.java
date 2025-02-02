package com.shop.microservices.product.Dto;

import com.shop.microservices.product.Exception.FieldValidationException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for creating or updating a product in the system.
 * This DTO contains the necessary information for a product request, such as the name, description, and price.
 * Validation annotations ensure that the incoming data meets business requirements.
 */
@Getter
@ApiModel(description = "Request DTO for creating or updating a product.")
public class ProductRequestDTO {

    /**
     * The name of the product.
     * The name must not be blank and is required for creating or updating a product.
     * -- GETTER --
     *  Gets the name of the product.
     *
     * -- SETTER --
     *  Sets the name of the product.
     *
     */
    @Setter
    @NotBlank(message = "Product name must not be blank")
    @ApiModelProperty(value = "The name of the product", required = true, example = "Smartphone")
    private String name;

    /**
     * A detailed description of the product.
     * The description is optional but should be a string.
     * -- GETTER --
     *  Gets the description of the product.
     *
     * -- SETTER --
     *  Sets the description of the product.
     *
      */
    @Setter
    @ApiModelProperty(value = "A detailed description of the product", required = false, example = "A high-end smartphone with a large display.")
    private String description;

    /**
     * The price of the product.
     * The price must not be null and must be a positive value greater than 0.
     * -- GETTER --
     *  Gets the price of the product.
     *
     * @return The price of the product.

     */
    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @ApiModelProperty(value = "The price of the product", required = true, example = "499.99")
    private BigDecimal price;

    /**
     * Sets the price of the product.
     * Price must be greater than 0, and if invalid, an exception will be thrown.
     *
     * @param price The price to set.
     * @throws IllegalArgumentException if the price is less than or equal to 0.
     */
    public void setPrice(BigDecimal price){
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new FieldValidationException("prod.error.3103", new Object[]{price});
        }
        this.price = price;
    }
}