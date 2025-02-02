package com.shop.microservices.product.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents a product category in the shop's catalog.
 * This class is mapped to the "category" collection in MongoDB.
 * The category contains details such as its name, description, and unique identifier.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "category")
public class Category {

    /**
     * Unique identifier for the category.
     */
    @Id
    @NotNull(message = "Category ID must not be null")
    private UUID categoryId;

    /**
     * The name of the category.
     * The name must not be blank.
     */
    @NotBlank(message = "Category name must not be blank")
    private String name;

    /**
     * A detailed description of the category.
     * The description may be blank.
     */
    private String description;
}
