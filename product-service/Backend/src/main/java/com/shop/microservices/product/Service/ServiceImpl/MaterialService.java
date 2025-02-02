package com.shop.microservices.product.Service.ServiceImpl;

import com.shop.microservices.product.Dto.MaterialRequestDTO;
import com.shop.microservices.product.Dto.MaterialResponseDTO;
import com.shop.microservices.product.Exception.InvalidInputException;
import com.shop.microservices.product.Exception.ResourceNotFoundException;
import com.shop.microservices.product.Exception.UniqueConstraintViolationException;
import com.shop.microservices.product.Mapper.MaterialMapper;
import com.shop.microservices.product.Model.Material;
import com.shop.microservices.product.Repository.MaterialRepository;
import com.shop.microservices.product.Service.ServiceInterface.IMaterialService;
import com.shop.microservices.product.Utils.MaterialValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for managing materials.
 * Provides methods for creating, updating, retrieving, and deleting materials.
 * Ensures validation, exception handling, and mapping between entities and DTOs.
 */
@Slf4j
@Service
public class MaterialService implements IMaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;
    private final MaterialValidationUtil materialValidationUtil;

    /**
     * Constructor for MaterialService.
     *
     * @param materialRepository   the repository for material operations
     * @param materialMapper       the mapper for converting between entity and DTO
     * @param materialValidationUtil utility for validating material-related data
     */
    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper, MaterialValidationUtil materialValidationUtil) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
        this.materialValidationUtil = materialValidationUtil;
    }

    /**
     * Creates a new material.
     *
     * @param materialRequestDTO the data for creating a material
     * @return the created material as a response DTO
     * @throws InvalidInputException if the input data is null or invalid
     * @throws UniqueConstraintViolationException if the material name is not unique
     */
    @Override
    @Transactional
    public MaterialResponseDTO createMaterial(MaterialRequestDTO materialRequestDTO) {
        if (materialRequestDTO == null) {
            throw new InvalidInputException("prod.error.3600");
        }

        validateMaterialRequest(materialRequestDTO);
        Material material = materialMapper.materialRequestDTOToMaterial(materialRequestDTO);
        Material savedMaterial = materialRepository.save(material);

        log.info("Material created with ID: {}", savedMaterial.getMaterialId());
        return materialMapper.materialToMaterialResponseDTO(savedMaterial);
    }

    /**
     * Updates an existing material.
     *
     * @param materialIdStr       the ID of the material to update
     * @param materialRequestDTO  the data to update the material with
     * @return the updated material as a response DTO
     * @throws InvalidInputException if the input data or material ID is invalid
     * @throws ResourceNotFoundException if the material with the given ID does not exist
     */
    @Override
    public MaterialResponseDTO updateMaterial(String materialIdStr, MaterialRequestDTO materialRequestDTO) {
        if (materialRequestDTO == null) {
            throw new InvalidInputException("prod.error.3600");
        }

        UUID materialId;
        try {
            materialId = UUID.fromString(materialIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3602");
        }

        Material existingMaterial = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3603", materialId));

        if (materialRequestDTO.getMaterialName() != null && !materialRequestDTO.getMaterialName().isBlank()) {
            existingMaterial.setMaterialName(materialRequestDTO.getMaterialName());
        }
        if (materialRequestDTO.getMaterialType() != null && !materialRequestDTO.getMaterialType().isBlank()) {
            existingMaterial.setMaterialType(materialRequestDTO.getMaterialType());
        }
        if (materialRequestDTO.getDescription() != null && !materialRequestDTO.getDescription().isBlank()) {
            existingMaterial.setDescription(materialRequestDTO.getDescription());
        }

        Material updatedMaterial = materialRepository.save(existingMaterial);
        return materialMapper.materialToMaterialResponseDTO(updatedMaterial);
    }

    /**
     * Retrieves a paginated list of all materials.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a paginated list of material response DTOs
     * @throws ResourceNotFoundException if no materials are found
     */
    @Override
    public Page<MaterialResponseDTO> getAllMaterials(int page, int size) {
        Page<Material> materials = materialRepository.findAll(PageRequest.of(page, size));
        if (materials.isEmpty()) {
            throw new ResourceNotFoundException("prod.error.3604");
        }
        return materials.map(materialMapper::materialToMaterialResponseDTO);
    }

    /**
     * Retrieves a material by its ID.
     *
     * @param materialIdStr the ID of the material to retrieve
     * @return the material as a response DTO
     * @throws InvalidInputException if the material ID is invalid
     * @throws ResourceNotFoundException if the material with the given ID does not exist
     */
    @Override
    public MaterialResponseDTO getMaterialById(String materialIdStr) {
        UUID materialId;
        try {
            materialId = UUID.fromString(materialIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3602");
        }
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3603", materialId));

        return materialMapper.materialToMaterialResponseDTO(material);
    }

    /**
     * Retrieves a material by its name and type.
     * This method searches for a material that matches both the provided {@code materialName}
     * and {@code materialType}. If no matching material is found, a {@link ResourceNotFoundException} is thrown.
     *
     * @param materialName the name of the material to retrieve
     * @param materialType the type of the material to retrieve
     * @return a {@link MaterialResponseDTO} containing the material details
     * @throws ResourceNotFoundException if no material with the given name and type is found
     */
    @Override
    public MaterialResponseDTO getMaterialByNameAndType(String materialName, String materialType) {
        Material material = materialRepository.findByMaterialNameAndMaterialType(materialName, materialType);
        if (material == null) {
            throw new ResourceNotFoundException("prod.error.3605", materialName);
        }
        return materialMapper.materialToMaterialResponseDTO(material);
    }


    /**
     * Deletes a material by its ID.
     *
     * @param materialIdStr the ID of the material to delete
     * @return the ID of the deleted material
     * @throws InvalidInputException if the material ID is invalid
     * @throws ResourceNotFoundException if the material with the given ID does not exist
     */
    @Override
    public String deleteMaterial(String materialIdStr) {
        UUID materialId;
        try {
            materialId = UUID.fromString(materialIdStr);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException("prod.error.3602");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("prod.error.3603", materialId));

        materialRepository.delete(material);
        log.info("Material with ID: {} successfully deleted", materialId);
        return materialIdStr;
    }

    /**
     * Validates the material request for uniqueness of name.
     *
     * @param materialRequestDTO the material request to validate
     * @throws UniqueConstraintViolationException if the material name is not unique
     */
    private void validateMaterialRequest(MaterialRequestDTO materialRequestDTO) {
        if (!materialValidationUtil.isMaterialNameAndTypeUnique(materialRequestDTO.getMaterialName(), materialRequestDTO.getMaterialType())) {
            throw new UniqueConstraintViolationException("prod.error.3601",
                    new String[]{materialRequestDTO.getMaterialName(), materialRequestDTO.getMaterialType()});

        }
    }
}
