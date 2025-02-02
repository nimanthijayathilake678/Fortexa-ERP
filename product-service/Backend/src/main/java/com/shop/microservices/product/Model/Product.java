package com.shop.microservices.product.Model;

import com.shop.microservices.product.Exception.FieldValidationException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a product in the shop's catalog.
 * This class is mapped to the "product" collection in MongoDB.
 * The product contains details such as name, description, and price.
 */
@Getter
@Setter
@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    /**
     * Unique identifier for the product.
     */
    @Id
    private UUID id;

    /**
     * The name of the product.
     * The name must not be blank and must be unique in the collection.
     */
    @NotBlank(message = "Product name must not be blank")
    @Indexed(unique = true, name = "unique_product_name")
    private String name;

    /**
     * A detailed description of the product.
     * The description may be blank.
     */
    private String description;

    /**
     * The price of the product.
     * Price must be greater than 0.01 to ensure it's a valid positive value.
     */
    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    /**
     * Sets the price for the product, ensuring that only positive values are allowed.
     * <p>
     * This method validates that the provided price is a positive value and throws an exception if the price is
     * zero or negative. It ensures that the price cannot be set to an invalid value.
     * </p>
     *
     * @param price The price to set for the product. Must be a positive value.
     * @throws FieldValidationException if the price is null, zero, or negative.
     */
    public void setPrice(BigDecimal price){
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new FieldValidationException("prod.error.3103", new Object[]{price});
        }
        this.price = price;
    }

}
