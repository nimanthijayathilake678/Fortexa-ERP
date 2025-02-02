package com.shop.microservices.product.Service.ServiceInterface;

import com.shop.microservices.product.Dto.CategoryRequestDTO;
import com.shop.microservices.product.Dto.CategoryResponseDTO;
import org.springframework.data.domain.Page;

/**
 * ICategoryService interface defines the contract for the CategoryService class.
 * It declares the essential CRUD operations for managing product categories.
 */
public interface ICategoryService {

    /**
     * Creates a new category.
     *
     * @param categoryRequestDTO The DTO containing the category data.
     * @return A {@link CategoryResponseDTO} representing the created category.
     */
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    /**
     * Updates an existing category.
     *
     * @param categoryIdStr The ID of the category in String format to update.
     * @param categoryRequestDTO The DTO containing the updated category data.
     * @return A {@link CategoryResponseDTO} representing the updated category.
     */
    CategoryResponseDTO updateCategory(String categoryIdStr, CategoryRequestDTO categoryRequestDTO);

    /**
     * Retrieves a paginated list of all categories.
     *
     * @param page The page number to retrieve (0-based index).
     * @param size The number of categories to include per page.
     * @return A {@link Page} object containing a list of {@link CategoryResponseDTO} objects for the requested page,
     *         along with pagination metadata such as total pages and total elements.
     */
    Page<CategoryResponseDTO> getAllCategory(int page, int size);

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryIdStr The ID of the category in String format to fetch.
     * @return A {@link CategoryResponseDTO} representing the requested category.
     */
    CategoryResponseDTO getCategoryById(String categoryIdStr);

    /**
     * Retrieves a category by its name.
     *
     * @param categoryName The name of the category to fetch.
     * @return A {@link CategoryResponseDTO} representing the requested category.
     */
    CategoryResponseDTO getCategoryByName(String categoryName);

    /**
     * Deletes a category by its ID.
     *
     * @param categoryIdStr The ID of the category in String format to delete.
     * @return A confirmation message as a String.
     */
    String deleteCategory(String categoryIdStr);
}

