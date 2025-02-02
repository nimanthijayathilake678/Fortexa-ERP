package com.shop.microservices.product.Controller;


import com.shop.microservices.product.Dto.CategoryRequestDTO;
import com.shop.microservices.product.Dto.CategoryResponseDTO;
import com.shop.microservices.product.Service.ServiceInterface.ICategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to manage category-related operations such as creating, updating, retrieving, and deleting categories.
 *
 * <p>This controller exposes APIs for creating categories and retrieving them with pagination.</p>
 */
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "APIs for managing categories")
public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * Creates a new category based on the provided category data.
     *
     * <p>This endpoint accepts a {@link CategoryRequestDTO} containing the category details,
     * validates and processes the request, and returns the created category in {@link CategoryResponseDTO} format.</p>
     *
     * @param categoryRequest The category data from the client in {@link CategoryRequestDTO} format.
     * @return A {@link ResponseEntity} containing the created category as {@link CategoryResponseDTO}.
     */
    @Operation(summary = "Create a new category", description = "Creates a new category in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category created successfully",
                            content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid category data")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody @Parameter(description = "Category data to be created", required = true) CategoryRequestDTO categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    /**
     * Updates an existing category based on the provided category data and category ID.
     *
     * @param categoryId The ID of the category to be updated.
     * @param categoryRequestDTO The category data from the client in {@link CategoryRequestDTO} format.
     * @return A {@link ResponseEntity} containing the updated category as {@link CategoryResponseDTO}.
     */
    @Operation(summary = "Update an existing category", description = "Updates an existing category based on the provided data and ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully",
                            content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid category data")
            })
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable @Parameter(description = "Category ID of the category to be updated", required = true) String categoryId,
            @RequestBody @Parameter(description = "Category details to update") CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryRequestDTO));
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page The page number to retrieve (0-based index). Defaults to 0 if not provided.
     * @param size The number of categories per page. Defaults to 10 if not provided.
     * @return A {@link ResponseEntity} containing a {@link Page} of {@link CategoryResponseDTO} objects.
     */
    @Operation(summary = "Retrieve paginated categories", description = "Fetches categories with pagination support",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of categories",
                            content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number (default is 0)", required = false) int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size (default is 10)", required = false) int size) {
        Page<CategoryResponseDTO> categories = categoryService.getAllCategory(page, size);
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves the details of a specific category based on the provided category ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A {@link ResponseEntity} containing the category details as {@link CategoryResponseDTO}.
     */
    @Operation(summary = "Get category by ID", description = "Retrieves the details of a specific category by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @Parameter(description = "The ID of the category to retrieve", required = true) @PathVariable String categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    /**
     * Retrieves the details of a specific category based on its name.
     *
     * @param categoryName The name of the category to retrieve.
     * @return A {@link ResponseEntity} containing the category details as {@link CategoryResponseDTO}.
     */
    @Operation(summary = "Get category by name", description = "Retrieve the category details based on the provided category name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category",
                            content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    @GetMapping("/name/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(
            @Parameter(description = "The name of the category to retrieve", required = true) @PathVariable String categoryName) {
        return ResponseEntity.ok(categoryService.getCategoryByName(categoryName));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId The ID of the category to delete.
     * @return A {@link ResponseEntity} containing a confirmation message upon successful deletion.
     */
    @Operation(summary = "Delete a category", description = "Deletes a category by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "The ID of the category to delete", required = true) @PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

