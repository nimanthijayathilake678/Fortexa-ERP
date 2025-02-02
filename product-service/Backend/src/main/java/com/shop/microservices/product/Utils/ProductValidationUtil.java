package com.shop.microservices.product.Utils;

import com.shop.microservices.product.Repository.ProductRepository;
import org.springframework.stereotype.Component;

/**
 * Utility class for validating product-related data, particularly focusing on ensuring
 * This class provides utility methods used during product creation or modification.
 */
@Component
public class ProductValidationUtil {

    // Repository for querying product data from the database
    private final ProductRepository productRepository;

    /**
     * Constructor for initializing the ProductRequestValidationUtil.
     * The repository is injected here to allow validation checks to query the product data store.
     *
     * @param productRepository The repository used to query the database for products related data.
     */
    public ProductValidationUtil(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Checks if the provided product name is unique within the product repository.
     * This method queries the repository to determine whether the product name already exists.
     *
     * @param productName The name of the product to be validated.
     * @return {@code true} if the product name is unique, {@code false} if the name is already taken.
     */
    public boolean isProductNameUnique(String productName) {
        // If the product name already exists, return false
        return !productRepository.existsByName(productName);
    }
}
