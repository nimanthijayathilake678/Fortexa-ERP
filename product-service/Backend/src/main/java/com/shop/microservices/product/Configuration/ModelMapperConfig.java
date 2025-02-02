package com.shop.microservices.product.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to define application-wide beans.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Creates a ModelMapper bean for object mapping.
     *
     * @return a configured ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
