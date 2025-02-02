package com.shop.microservices.product.Controller;

import com.shop.microservices.product.Dto.MaterialRequestDTO;
import com.shop.microservices.product.Dto.MaterialResponseDTO;
import com.shop.microservices.product.Service.ServiceInterface.IMaterialService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing material-related operations such as creating, updating, retrieving, and deleting materials.
 *
 * <p>This controller exposes APIs for managing materials, including endpoints for creation, retrieval by ID or name/type,
 * updating, deleting, and pagination support for fetching materials.</p>
 */
@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {
    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * Creates a new material.
     *
     * <p>This endpoint accepts a {@link MaterialRequestDTO} with the material details and returns the created material in {@link MaterialResponseDTO} format.</p>
     *
     * @param materialRequest The material data to create.
     * @return A {@link ResponseEntity} containing the created material as {@link MaterialResponseDTO}.
     */
    @Operation(summary = "Create a new material", description = "Creates a new material in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Material created successfully",
                            content = @Content(schema = @Schema(implementation = MaterialResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid material data")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MaterialResponseDTO> createMaterial(
            @RequestBody @Parameter(description = "Material data to be created", required = true) MaterialRequestDTO materialRequest) {
        return ResponseEntity.ok(materialService.createMaterial(materialRequest));
    }

    /**
     * Updates an existing material.
     *
     * <p>This endpoint updates an existing material using the provided material ID and new details.</p>
     *
     * @param materialId The ID of the material to update.
     * @param materialRequestDTO The new material details.
     * @return A {@link ResponseEntity} containing the updated material as {@link MaterialResponseDTO}.
     */
    @Operation(summary = "Update an existing material", description = "Updates an existing material based on the provided data and ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Material updated successfully",
                            content = @Content(schema = @Schema(implementation = MaterialResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid material data")
            })
    @PutMapping("/{materialId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MaterialResponseDTO> updateMaterial(
            @PathVariable @Parameter(description = "Material ID of the material to be updated", required = true) String materialId,
            @RequestBody @Parameter(description = "Material details to update") MaterialRequestDTO materialRequestDTO) {
        return ResponseEntity.ok(materialService.updateMaterial(materialId, materialRequestDTO));
    }

    /**
     * Retrieves a paginated list of materials.
     *
     * <p>This endpoint returns a paginated list of materials, allowing clients to specify the page number and size.</p>
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of materials per page (default is 10).
     * @return A {@link ResponseEntity} containing a paginated list of materials as {@link MaterialResponseDTO}.
     */
    @Operation(summary = "Retrieve paginated materials", description = "Fetches materials with pagination support",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of materials",
                            content = @Content(schema = @Schema(implementation = MaterialResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<MaterialResponseDTO>> getAllMaterials(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number (default is 0)", required = false) int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size (default is 10)", required = false) int size) {
        Page<MaterialResponseDTO> materials = materialService.getAllMaterials(page, size);
        return ResponseEntity.ok(materials);
    }

    /**
     * Retrieves a specific material by its ID.
     *
     * <p>This endpoint fetches a single material by its unique ID.</p>
     *
     * @param materialId The ID of the material to retrieve.
     * @return A {@link ResponseEntity} containing the material details as {@link MaterialResponseDTO}.
     */
    @Operation(summary = "Get material by ID", description = "Retrieves the details of a specific material by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the material",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            })
    @GetMapping("/{materialId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MaterialResponseDTO> getMaterialById(
            @Parameter(description = "The ID of the material to retrieve", required = true) @PathVariable String materialId) {
        return ResponseEntity.ok(materialService.getMaterialById(materialId));
    }

    /**
     * Retrieves a material by its name and type.
     *
     * <p>This endpoint fetches a material based on its name and type.</p>
     *
     * @param materialName The name of the material to retrieve.
     * @param materialType The type of the material to retrieve.
     * @return A {@link ResponseEntity} containing the material details as {@link MaterialResponseDTO}.
     */
    @Operation(summary = "Get material by name and type", description = "Retrieve the material details based on the provided material name and type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the material",
                            content = @Content(schema = @Schema(implementation = MaterialResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            })
    @GetMapping("/name/{materialName}/type/{materialType}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MaterialResponseDTO> getMaterialByNameAndType(
            @Parameter(description = "The name of the material to retrieve", required = true) @PathVariable String materialName,
            @Parameter(description = "The type of the material to retrieve", required = true) @PathVariable String materialType) {
        return ResponseEntity.ok(materialService.getMaterialByNameAndType(materialName, materialType));
    }

    /**
     * Deletes a material by its ID.
     *
     * <p>This endpoint deletes a material using its unique ID.</p>
     *
     * @param materialId The ID of the material to delete.
     * @return A {@link ResponseEntity} containing a confirmation message upon successful deletion.
     */
    @Operation(summary = "Delete a material", description = "Deletes a material by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Material successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            })
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> deleteMaterial(
            @Parameter(description = "The ID of the material to delete", required = true) @PathVariable String materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.noContent().build();
    }
}
