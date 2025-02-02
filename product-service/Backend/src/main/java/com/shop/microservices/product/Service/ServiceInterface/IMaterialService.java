package com.shop.microservices.product.Service.ServiceInterface;

import com.shop.microservices.product.Dto.MaterialRequestDTO;
import com.shop.microservices.product.Dto.MaterialResponseDTO;
import org.springframework.data.domain.Page;

/**
 * IMaterialService interface defines the contract for the MaterialService class.
 * It declares the essential CRUD operations for managing materials.
 */
public interface IMaterialService {

    /**
     * Creates a new material.
     *
     * @param materialRequestDTO The DTO containing the material data.
     * @return A {@link MaterialResponseDTO} representing the created material.
     */
    MaterialResponseDTO createMaterial(MaterialRequestDTO materialRequestDTO);

    /**
     * Updates an existing material.
     *
     * @param materialIdStr The ID of the material in String format to update.
     * @param materialRequestDTO The DTO containing the updated material data.
     * @return A {@link MaterialResponseDTO} representing the updated material.
     */
    MaterialResponseDTO updateMaterial(String materialIdStr, MaterialRequestDTO materialRequestDTO);

    /**
     * Retrieves a paginated list of all materials.
     *
     * @param page The page number to retrieve (0-based index).
     * @param size The number of materials to include per page.
     * @return A {@link Page} object containing a list of {@link MaterialResponseDTO} objects for the requested page,
     *         along with pagination metadata such as total pages and total elements.
     */
    Page<MaterialResponseDTO> getAllMaterials(int page, int size);

    /**
     * Retrieves a material by its ID.
     *
     * @param materialIdStr The ID of the material in String format to fetch.
     * @return A {@link MaterialResponseDTO} representing the requested material.
     */
    MaterialResponseDTO getMaterialById(String materialIdStr);

    /**
     * Retrieves a material by its name and type.
     * This method fetches the material that matches the provided {@code materialName} and {@code materialType}.
     *
     * @param materialName the name of the material to fetch
     * @param materialType the type of the material to fetch
     * @return a {@link MaterialResponseDTO} representing the requested material
     *
     */
    MaterialResponseDTO getMaterialByNameAndType(String materialName, String materialType);


    /**
     * Deletes a material by its ID.
     *
     * @param materialIdStr The ID of the material in String format to delete.
     * @return A confirmation message as a String.
     */
    String deleteMaterial(String materialIdStr);
}
