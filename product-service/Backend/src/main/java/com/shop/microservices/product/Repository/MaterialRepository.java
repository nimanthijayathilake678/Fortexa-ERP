package com.shop.microservices.product.Repository;

import com.shop.microservices.product.Model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link Material} entities in MongoDB.
 * This interface extends {@link MongoRepository} to provide basic CRUD operations.
 * Custom queries can be added as necessary.
 */
@Repository
public interface MaterialRepository extends MongoRepository<Material, UUID> {

    /**
     * Checks if a material with the specified name and type exists in the repository.
     * This method generates a query based on the method name and the provided {@code materialName} and {@code materialType} parameters.
     *
     * @param materialName The name of the material to check.
     * @param materialType The type of the material to check.
     * @return {@code true} if a material with the specified name and type exists, {@code false} otherwise.
     */
    boolean existsByMaterialNameAndMaterialType(String materialName, String materialType);


    /**
     * Finds the material that matches the specified name and type.
     * This method generates a query based on the method name and the provided {@code materialName}
     * and {@code materialType} parameters, and returns the material that matches both criteria.
     *
     * <p>If no material is found with the specified name and type, an empty {@link Material} object is returned.</p>
     *
     * @param materialName The name of the material to search for.
     * @param materialType The type of the material to search for.
     * @return A {@link Material} object representing the material that matches the specified name and type.
     *         If no matching material is found, the object may be empty.
     */
    Material findByMaterialNameAndMaterialType(String materialName, String materialType);

}
