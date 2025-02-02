package com.shop.microservices.product.Exception;

/**
 * FieldValidationException is a custom exception that extends {@link ApplicationException}.
 * This exception is specifically used to handle field-level validation errors in the application,
 * providing a structured way to report validation failures related to specific fields.
 */
public class FieldValidationException extends ApplicationException {

    /**
     * Constructor for {@link FieldValidationException}.
     * This constructor creates a new instance of FieldValidationException with a specific error code
     * and message arguments for detailed error reporting.
     *
     * @param errorCode A unique error code that identifies the specific validation error.
     * @param messageArgs An array of objects representing arguments to format the error message.
     */
    public FieldValidationException(String errorCode, Object[] messageArgs) {
        super(errorCode, messageArgs);
    }
}
