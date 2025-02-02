package com.shop.microservices.product.Utils;

import com.shop.microservices.product.Repository.MaterialRepository;
import org.springframework.stereotype.Component;

/**
 * Utility class for validating Material-related data, particularly focusing on ensuring
 * the uniqueness of a material's name and type.
 * This class provides utility methods used during Material creation or modification.
 */
@Component
public class MaterialValidationUtil {

    private final MaterialRepository materialRepository;

    /**
     * Constructor for initializing the MaterialValidationUtil.
     * The repository is injected here to allow validation checks to query the Material data store.
     *
     * @param materialRepository The repository used to query the database for Material related data.
     */
    public MaterialValidationUtil(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Checks if the provided material name and type combination is unique within the Material repository.
     * This method queries the repository to determine whether the material with the given name and type already exists.
     *
     * @param materialName The name of the material to search for.
     * @param materialType The type of the material to search for.
     * @return {@code true} if the combination of material name and type is unique, {@code false} otherwise.
     */
    public boolean isMaterialNameAndTypeUnique(String materialName, String materialType) {
        return !materialRepository.existsByMaterialNameAndMaterialType(materialName, materialType);
    }
}
