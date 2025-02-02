package com.shop.microservices.product.Mapper;

import com.shop.microservices.product.Dto.ProductRequestDTO;
import com.shop.microservices.product.Dto.ProductResponseDTO;
import com.shop.microservices.product.Model.Product;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for transforming product-related data between DTOs and domain objects.
 * It uses ModelMapper to convert between the DTOs ({@link ProductRequestDTO}, {@link ProductResponseDTO}) and the {@link Product} entity.
 */
@Component
public class ProductMapper {
    // Instance of ModelMapper to handle object mapping
    private final ModelMapper modelMapper;

    /**
     * Constructor for the productMapper class.
     *  Initializes the ModelMapper instance used for object mapping.
     * @param modelMapper The ModelMapper instance that will be used to map objects between different types.
     */
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Configures the mapping to handle Product -> ProductResponseDTO mapping for records.
     */
    @PostConstruct
    public void configureMappings() {
        // Map Product to ProductResponseDTO using the constructor
        TypeMap<Product, ProductResponseDTO> typeMap = modelMapper.createTypeMap(Product.class, ProductResponseDTO.class);
        typeMap.setProvider(provision -> {
            Product source = (Product) provision.getSource();
            return new ProductResponseDTO(
                    source.getId(),
                    source.getName(),
                    source.getDescription(),
                    source.getPrice()
            );
        });
    }

    /**
     * Converts a ProductRequestDTO to a Product entity.
     * This method maps the fields of ProductRequestDTO to the corresponding fields in the Product entity.
     *
     * @param productRequestDTO the DTO containing the product data.
     * @return the mapped Product entity.
     */
    public Product productRequestDTOToProduct(ProductRequestDTO productRequestDTO){
        return modelMapper.map(productRequestDTO, Product.class);
    }

    /**
     * Converts a Product entity to a productResponseDTO.
     * This method maps the fields of product entity to the corresponding fields in the product entity.
     *
     * @param product The Product entity to be converted.
     * @return The mapped ProductResponseDTO.
     */
    public ProductResponseDTO productToProductResponseDTO(Product product){
        return modelMapper.map(product, ProductResponseDTO.class);
    }
}
