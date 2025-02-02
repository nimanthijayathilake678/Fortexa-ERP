package com.shop.microservices.product.Exception;

/**
 * Exception thrown when an error occurs during the creation of an entity.
 * <p>
 * This exception extends {@link ApplicationException} and is intended to be used for
 * errors specifically related to the creation of entities in the application. It allows
 * for the inclusion of an error code and additional message arguments to provide
 * detailed context about the error.
 * </p>
 * <p>
 * Usage:
 * <ul>
 *     <li>Throw this exception when an entity fails to be created due to validation, database, or other issues.</li>
 *     <li>Use the error code and message arguments to provide more descriptive error information.</li>
 * </ul>
 * </p>
 */
public class EntityCreationException extends ApplicationException {

    /**
     * Constructs a new EntityCreationException with a specific error code and the root cause of the exception.
     * <p>
     * This constructor is useful when the exception is caused by another lower-level exception.
     * </p>
     *
     * @param errorCode A specific error code indicating the nature of the error (e.g., "entity.creation.error").
     * @param cause     The root cause of the exception, which can be used for debugging.
     */
    public EntityCreationException(String errorCode, Throwable cause) {
        super(errorCode, null, cause);
    }

    /**
     * Constructs a new EntityCreationException with a specific error code.
     * <p>
     * This constructor is useful for application-specific errors that are not caused by lower-level exceptions.
     * </p>
     *
     * @param errorCode A specific error code indicating the nature of the error (e.g., "entity.creation.error").
     */
    public EntityCreationException(String errorCode) {
        super(errorCode, null);
    }
}
