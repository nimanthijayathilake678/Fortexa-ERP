package com.shop.microservices.product.Mapper;

import com.shop.microservices.product.Dto.MaterialRequestDTO;
import com.shop.microservices.product.Dto.MaterialResponseDTO;
import com.shop.microservices.product.Model.Material;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for transforming material-related data between DTOs and domain objects.
 * It uses ModelMapper to convert between the DTOs ({@link MaterialRequestDTO}, {@link MaterialResponseDTO}) and the {@link Material} entity.
 */
@Component
public class MaterialMapper {

    // Instance of ModelMapper to handle object mapping
    private final ModelMapper modelMapper;

    /**
     * Constructor for the MaterialMapper class.
     * Initializes the ModelMapper instance used for object mapping.
     * @param modelMapper The ModelMapper instance that will be used to map objects between different types.
     */
    public MaterialMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Configures the mapping to handle Material -> MaterialResponseDTO mapping for records.
     * This method uses a provider to handle the transformation of the Material entity into the MaterialResponseDTO.
     */
    @PostConstruct
    public void configureMappings() {
        // Map Material to MaterialResponseDTO using the constructor
        TypeMap<Material, MaterialResponseDTO> typeMap = modelMapper.createTypeMap(Material.class, MaterialResponseDTO.class);
        typeMap.setProvider(provision -> {
            Material source = (Material) provision.getSource();
            return new MaterialResponseDTO(
                    source.getMaterialId(),
                    source.getMaterialName(),
                    source.getMaterialType(),
                    source.getDescription()
            );
        });
    }

    /**
     * Converts a MaterialRequestDTO to a Material entity.
     * This method maps the fields of MaterialRequestDTO to the corresponding fields in the Material entity.
     *
     * @param materialRequestDTO DTO containing the Material data.
     * @return the mapped {@link Material} entity.
     */
    public Material materialRequestDTOToMaterial(MaterialRequestDTO materialRequestDTO) {
        return modelMapper.map(materialRequestDTO, Material.class);
    }

    /**
     * Converts a {@link Material} entity to a {@link MaterialResponseDTO}.
     * This method maps the fields of Material entity to the corresponding fields in the MaterialResponseDTO entity.
     *
     * @param material The Material entity to be converted.
     * @return The mapped {@link MaterialResponseDTO}.
     */
    public MaterialResponseDTO materialToMaterialResponseDTO(Material material) {
        return modelMapper.map(material, MaterialResponseDTO.class);
    }
}
