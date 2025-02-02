package com.shop.microservices.product.Exception;

import java.util.UUID;

/**
 * Custom exception class for handling cases where a requested resource is not found.
 * This class extends {@link ApplicationException} and provides constructors
 * for creating exceptions with an error code and optional resource identifier.
 */
public class ResourceNotFoundException extends ApplicationException {

    /**
     * Constructor to create a {@link ResourceNotFoundException} with an error code.
     * This can be used when the specific ID of the missing resource is not required.
     *
     * @param errorCode The error code representing the type of resource not found.
     */
    public ResourceNotFoundException(String errorCode) {
        super(errorCode, null);
    }

    /**
     * Constructor to create a {@link ResourceNotFoundException} with an error code
     * and the UUID of the resource that was not found.
     * This allows the error to include specific information about the missing resource.
     *
     * @param errorCode The error code representing the type of resource not found.
     * @param id        The unique identifier of the resource that could not be found.
     */
    public ResourceNotFoundException(String errorCode, UUID id) {
        super(errorCode, new Object[]{id});
    }

    /**
     * Constructor to create a {@link ResourceNotFoundException} with an error code
     * and the name of the resource that was not found.
     * This allows the error to include specific information about the missing resource.
     *
     * @param errorCode The error code representing the type of resource not found.
     * @param name        The name of the resource that could not be found.
     */
    public ResourceNotFoundException(String errorCode, String name) {
        super(errorCode, new Object[]{name});
    }
}

