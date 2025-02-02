package com.shop.microservices.product.Listener;

import com.shop.microservices.product.Model.Category;
import com.shop.microservices.product.Model.Material;
import com.shop.microservices.product.Model.Product;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A consolidated event listener for handling entity-related events in the shop's system.
 * This listener ensures that entities (Product, Material, Category) have valid UUIDs before being persisted.
 * By centralizing the logic, this class minimizes boilerplate code and simplifies event handling.
 */
@Component
public class EntityEventListener {

    /**
     * Event listener triggered before an entity is converted and persisted in the MongoDB database.
     * It ensures that entities have valid UUIDs if they do not already have one.
     *
     * @param event The event containing the entity object being persisted.
     */
    @EventListener
    public void handleBeforeConvert(BeforeConvertEvent<?> event) {
        Object source = event.getSource(); // Get the source of the event (entity)

        // Handle Product entity
        if (source instanceof Product product) {
            if (product.getId() == null) {
                product.setId(UUID.randomUUID());
            }
        }

        // Handle Material entity
        if (source instanceof Material material) {
            if (material.getMaterialId() == null) {
                material.setMaterialId(UUID.randomUUID());
            }
        }

        // Handle Category entity
        if (source instanceof Category category) {
            if (category.getCategoryId() == null) {
                category.setCategoryId(UUID.randomUUID());
            }
        }
    }
}
