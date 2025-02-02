package com.shop.microservices.product.Utils;

import com.shop.microservices.product.Repository.CategoryRepository;
import org.springframework.stereotype.Component;

/**
 * Utility class for validating Category-related data, particularly focusing on ensuring
 * This class provides utility methods used during Category creation or modification.
 */
@Component
public class CategoryValidationUtil {

    private final CategoryRepository categoryRepository;

    /**
     * Constructor for initializing the CategoryRequestValidationUtil.
     * The repository is injected here to allow validation checks to query the Category data store.
     *
     * @param categoryRepository The repository used to query the database for Category related data.
     */
    public CategoryValidationUtil(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Checks if the provided category name is unique within the Category repository.
     * This method queries the repository to determine whether the category name already exists.
     *
     * @param categoryName The name (or part of the name) of the category to search for.
     * @return  @return {@code true} if a category name is unique, {@code false} otherwise.
     */
    public boolean isCategoryNameUnique(String categoryName){
        return !categoryRepository.existsByName(categoryName);
    }
}
