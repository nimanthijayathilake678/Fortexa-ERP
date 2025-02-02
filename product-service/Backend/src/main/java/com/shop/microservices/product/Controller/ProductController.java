package com.shop.microservices.product.Controller;

import com.shop.microservices.product.Dto.ProductRequestDTO;
import com.shop.microservices.product.Dto.ProductResponseDTO;
import com.shop.microservices.product.Exception.EntityCreationException;
import com.shop.microservices.product.Service.ServiceInterface.IProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

/**
 * Controller to manage product-related operations such as creating and retrieving products.
 *
 * <p>This controller exposes APIs for creating products and retrieving them with pagination.</p>
 */
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final IProductService productService;

    /**
     * Creates a new product based on the provided product data.
     *
     * <p>This endpoint accepts a {@link ProductRequestDTO} containing the product details,
     * validates and processes the request, and returns the created product in {@link ProductResponseDTO} format.</p>
     *
     * @param productRequest The product data from the client in {@link ProductRequestDTO} format.
     * @return A {@link ResponseEntity} containing the created product as {@link ProductResponseDTO}.
     * @throws EntityCreationException If there is an issue during product creation.
     */
    @Operation(summary = "Create a new product", description = "Creates a new product in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid product data")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody @Parameter(description = "Product data to be created", required = true) ProductRequestDTO productRequest) {
        // Call the service layer to create a product and return a successful response
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    /**
     * Update the new product based on the provided product data and product ID.
     *
     * <p>This endpoint accepts a {@link ProductRequestDTO} containing the product details and Product ID,
     * validates and processes the request, and returns the updated product in {@link ProductResponseDTO} format.</p>
     *
     * @param productRequestDTO The product data from the client in {@link ProductRequestDTO} format.
     * @param productId The product ID of the product that need to update.
     * @return A {@link ResponseEntity} containing the created product as {@link ProductResponseDTO}.
     */
    @Operation(summary = "Update the new product", description = "Update the new product based on the provided product data and product ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid product data")
            })
    @PutMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable @Parameter(description = "Product id of the product needed to updated", required = true) String productId,
            @RequestBody @Parameter(description = "Product details that need to update") ProductRequestDTO productRequestDTO){

        // Call the update service to update the product and return updated product
        return ResponseEntity.ok(productService.updateProduct(productId, productRequestDTO));
    }

    /**
     * Retrieves a paginated list of products.
     *
     * <p>This endpoint fetches products with pagination support, based on the provided page number
     * and page size. Default values for pagination are page 0 and size 10 if not provided.</p>
     *
     * @param page The page number to retrieve (0-based index). Defaults to 0 if not provided.
     * @param size The number of products per page. Defaults to 10 if not provided.
     * @return A {@link ResponseEntity} containing a {@link Page} of {@link ProductResponseDTO} objects.
     */
    @Operation(summary = "Retrieve paginated products", description = "Fetches products with pagination support",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number (default is 0)", required = false) int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size (default is 10)", required = false) int size) {

        // Fetch paginated products from the service layer
        Page<ProductResponseDTO> products = productService.getAllProducts(page, size);

        // Return the paginated result wrapped in ResponseEntity
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieve the product details based on the provided product ID.
     *
     * <p>This endpoint fetches a products  based on the provided product ID.</p>
     *
     * @param productId The ID of the product that need to retrieve
     * @return A {@link ResponseEntity}  of a {@link ProductResponseDTO} objects.
     */
    @Operation(
            summary = "Get product by ID",
            description = "Retrieve the product details based on the provided product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "The ID of the product to retrieve", required = true)
            @PathVariable String productId) {
        // Return the product based on provided product id
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /**
     * Deletes a product by its ID.
     * <p>
     * Validates the product ID string input, converts it to a UUID, and deletes the product
     * from the database if it exists. Throws appropriate exceptions for invalid input or if
     * the product is not found.
     * </p>
     *
     * @param productId The ID of the product to be deleted, as a string.
     * @return A {@link ResponseEntity} containing a confirmation message upon successful deletion.
     */
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its ID. Validates the input and ensures the product exists in the database.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable String productId) {
        // Delegate the deletion logic to the service layer
        productService.deleteProduct(productId);

        // Return a confirmation response
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Product successfully deleted"));
    }

}
