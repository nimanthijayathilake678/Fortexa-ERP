package com.shop.microservices.product.Mapper;

import com.shop.microservices.product.Dto.CategoryRequestDTO;
import com.shop.microservices.product.Dto.CategoryResponseDTO;
import com.shop.microservices.product.Model.Category;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for transforming category-related data between DTOs and domain objects.
 * It uses ModelMapper to convert between the DTOs ({@link CategoryRequestDTO}, {@link CategoryResponseDTO}) and the {@link Category} entity.
 */
@Component
public class CategoryMapper {

    // Instance of ModelMapper to handle object mapping
    private final ModelMapper modelMapper;

    /**
     * Constructor for the productMapper class.
     *  Initializes the ModelMapper instance used for object mapping.
     * @param modelMapper The ModelMapper instance that will be used to map objects between different types.
     */
    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Configures the mapping to handle Category -> CategoryResponseDTO mapping for records.
     */
    @PostConstruct
    public void configureMappings() {
        // Map Product to ProductResponseDTO using the constructor
        TypeMap<Category, CategoryResponseDTO> typeMap = modelMapper.createTypeMap(Category.class, CategoryResponseDTO.class);
        typeMap.setProvider(provision -> {
            Category source = (Category) provision.getSource();
            return new CategoryResponseDTO(
                    source.getCategoryId(),
                    source.getName(),
                    source.getDescription()
            );
        });
    }

    /**
     * Converts a ProductRequestDTO to a Product entity.
     * This method maps the fields of ProductRequestDTO to the corresponding fields in the Product entity.
     *
     * @param categoryRequestDTO DTO containing the Category data.
     * @return the mapped {@link Category} entity.
     */
    public Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO){
        return modelMapper.map(categoryRequestDTO, Category.class);
    }

    /**
     * Converts a {@link Category} entity to a {@link CategoryResponseDTO}.
     * This method maps the fields of category entity to the corresponding fields in the CategoryResponseDTO entity.
     *
     * @param category The Product entity to be converted.
     * @return The mapped {@link CategoryResponseDTO}.
     */
    public CategoryResponseDTO categoryToCategoryResponseDTO(Category category){
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

}
