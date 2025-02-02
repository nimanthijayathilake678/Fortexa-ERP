package com.shop.microservices.product.Service.ServiceImpl;

import com.shop.microservices.product.Dto.CategoryRequestDTO;
import com.shop.microservices.product.Dto.CategoryResponseDTO;
import com.shop.microservices.product.Exception.InvalidInputException;
import com.shop.microservices.product.Exception.ResourceNotFoundException;
import com.shop.microservices.product.Exception.UniqueConstraintViolationException;
import com.shop.microservices.product.Mapper.CategoryMapper;
import com.shop.microservices.product.Model.Category;
import com.shop.microservices.product.Repository.CategoryRepository;
import com.shop.microservices.product.Service.ServiceInterface.ICategoryService;
import com.shop.microservices.product.Utils.CategoryValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for managing categories.
 * Provides methods for creating, updating, retrieving, and deleting categories.
 * Ensures validation, exception handling, and mapping between entities and DTOs.
 */
@Slf4j
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidationUtil categoryValidationUtil;

    /**
     * Constructor for CategoryService.
     *
     * @param categoryRepository   the repository for category operations
     * @param categoryMapper       the mapper for converting between entity and DTO
     * @param categoryValidationUtil utility for validating category-related data
     */
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryValidationUtil categoryValidationUtil) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryValidationUtil = categoryValidationUtil;
    }

    /**
     * Creates a new category.
     *
     * @param categoryRequestDTO the data for creating a category
     * @return the created category as a response DTO
     * @throws InvalidInputException if the input data is null or invalid
     * @throws UniqueConstraintViolationException if the category name is not unique
     */
    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRequestDTO == null) {
            throw new InvalidInputException("prod.error.3500");
        }

        validateCategoryRequest(categoryRequestDTO);
        Category category = categoryMapper.categoryRequestDTOToCategory(categoryRequestDTO);
        Category savedCategory = categoryRepository.save(category);

        log.info("Category created with ID: {}", savedCategory.getCategoryId());
        return categoryMapper.categoryToCategoryResponseDTO(savedCategory);
    }

    /**
     * Updates an existing category.
     *
     * @param categoryIdStr       the ID of the category to update
     * @param categoryRequestDTO  the data to update the category with
     * @return the updated category as a response DTO
     * @throws InvalidInputException if the input data or category ID is invalid
     * @throws ResourceNotFoundException if the category with the given ID does not exist
     */
    @Override
    public CategoryResponseDTO updateCategory(String categoryIdStr, CategoryRequestDTO categoryRequestDTO) {
        if (categoryRequestDTO == null) {
            throw new InvalidInputException("prod.error.3500");
        }

        UUID categoryId;
        try {
            categoryId = UUID.fromString(categoryIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3502");
        }

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3503", categoryId));

        if (categoryRequestDTO.getName() != null && !categoryRequestDTO.getName().isBlank()) {
            existingCategory.setName(categoryRequestDTO.getName());
        }
        if (categoryRequestDTO.getDescription() != null && !categoryRequestDTO.getDescription().isBlank()) {
            existingCategory.setDescription(categoryRequestDTO.getDescription());
        }

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponseDTO(updatedCategory);
    }

    /**
     * Retrieves a paginated list of all categories.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a paginated list of category response DTOs
     * @throws ResourceNotFoundException if no categories are found
     */
    @Override
    public Page<CategoryResponseDTO> getAllCategory(int page, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, size));
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("prod.error.3504");
        }
        return categories.map(categoryMapper::categoryToCategoryResponseDTO);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryIdStr the ID of the category to retrieve
     * @return the category as a response DTO
     * @throws InvalidInputException if the category ID is invalid
     * @throws ResourceNotFoundException if the category with the given ID does not exist
     */
    @Override
    public CategoryResponseDTO getCategoryById(String categoryIdStr) {
        UUID categoryId;
        try {
            categoryId = UUID.fromString(categoryIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3502");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3503", categoryId));

        return categoryMapper.categoryToCategoryResponseDTO(category);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param categoryName the name of the category to retrieve
     * @return the category as a response DTO
     * @throws ResourceNotFoundException if the category with the given name does not exist
     */
    @Override
    public CategoryResponseDTO getCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new ResourceNotFoundException("prod.error.3505", categoryName);
        }
        return categoryMapper.categoryToCategoryResponseDTO(category);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryIdStr the ID of the category to delete
     * @return the ID of the deleted category
     * @throws InvalidInputException if the category ID is invalid
     * @throws ResourceNotFoundException if the category with the given ID does not exist
     */
    @Override
    public String deleteCategory(String categoryIdStr) {
        UUID categoryId;
        try {
            categoryId = UUID.fromString(categoryIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3502");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3503", categoryId));

        categoryRepository.delete(category);
        log.info("Product with ID: {} successfully deleted", categoryId);
        return categoryIdStr;
    }

    /**
     * Validates the category request for uniqueness of name.
     *
     * @param categoryRequestDTO the category request to validate
     * @throws UniqueConstraintViolationException if the category name is not unique
     */
    private void validateCategoryRequest(CategoryRequestDTO categoryRequestDTO) {
        if (!categoryValidationUtil.isCategoryNameUnique(categoryRequestDTO.getName())) {
            throw new UniqueConstraintViolationException("prod.error.3501", "Name", categoryRequestDTO.getName());
        }
    }
}
