package com.shop.microservices.product.Repository;

import com.shop.microservices.product.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing {@link Product} entities in MongoDB.
 * This interface extends {@link MongoRepository} to provide basic CRUD operations.
 * Custom queries can be added as necessary.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {

    /**
     * Finds all products that contain the specified name.
     * This method generates a query based on the method name and the name parameter.
     *
     * @param name The name of the product.
     * @return A list of {@link Product} objects with the matching name.
     */
    List<Product> findByNameContaining(String name);

    /**
     * Finds all products with a price greater than or equal to the specified value.
     *
     * @param price The price threshold.
     * @return A list of {@link Product} objects with price greater than or equal to the specified value.
     */
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);

    /**
     * Checks if a product with the specified name exists in the repository.
     *
     * @param name The name of the product to check.
     * @return {@code true} if a product with the specified name exists, {@code false} otherwise.
     */
    boolean existsByName(String name);

    /**
     * Finds the product that contain the specified name.
     * This method generates a query based on the method name and the provided {@code name} parameter.
     * It returns the product that match the given full product name.
     *
     * @param productName The name of the product to search for.
     * @return A  {@link Product} object containing the specified name.
     *         The object may be empty if no product match the name.
     */
    Product findByName(String productName);
}
