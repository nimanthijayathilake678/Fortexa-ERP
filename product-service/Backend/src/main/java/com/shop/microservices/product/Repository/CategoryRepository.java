package com.shop.microservices.product.Repository;

import com.shop.microservices.product.Model.Category;
import com.shop.microservices.product.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Repository interface for managing {@link Category} entities in MongoDB.
 * This interface extends {@link MongoRepository} to provide basic CRUD operations.
 * Custom queries can be added as necessary.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, UUID> {

    /**
     * Checks if a Category with the specified name exists in the repository.
     *
     * @param name The name of the product to check.
     * @return {@code true} if a category with the specified name exists, {@code false} otherwise.
     */
    boolean existsByName(String name);

    /**
     * Finds the category that contain the specified name.
     * This method generates a query based on the method name and the provided {@code name} parameter.
     * It returns the category that match the given full category name.
     *
     * @param categoryName The name of the category to search for.
     * @return A  {@link Category} object containing the specified name.
     *         The object may be empty if no category match the name.
     */
    Category findByName(String categoryName);
}
