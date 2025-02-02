package com.shop.microservices.product.Exception;

/**
 * Exception thrown when a unique constraint violation occurs on a field (e.g., product name, email, etc.).
 * This exception is used to indicate that a value for a field must be unique across the system and that the
 * value provided is already in use.
 */
public class UniqueConstraintViolationException extends ApplicationException {

    /**
     * Constructor for UniqueConstraintViolationException with string value type.
     *
     * @param errorCode  The error code to be used for this exception.
     * @param fieldName  The name of the field that violated the unique constraint.
     * @param value      The value that violated the unique constraint.
     */
    public UniqueConstraintViolationException(String errorCode, String fieldName, String value) {
        super(errorCode, new Object[]{value, fieldName});
    }

    /**
     * Constructor for UniqueConstraintViolationException with integer value type.
     *
     * @param errorCode  The error code to be used for this exception.
     * @param fieldName  The name of the field that violated the unique constraint.
     * @param value      The integer value that violated the unique constraint.
     */
    public UniqueConstraintViolationException(String errorCode, String fieldName, int value) {
        super(errorCode, new Object[]{value, fieldName});
    }

    /**
     * Constructor for UniqueConstraintViolationException with boolean value type.
     *
     * @param errorCode  The error code to be used for this exception.
     * @param fieldName  The name of the field that violated the unique constraint.
     * @param value      The boolean value that violated the unique constraint.
     */
    public UniqueConstraintViolationException(String errorCode, String fieldName, boolean value) {
        super(errorCode, new Object[]{value, fieldName});
    }

    /**
     * Constructor for UniqueConstraintViolationException with double value type.
     *
     * @param errorCode  The error code to be used for this exception.
     * @param fieldName  The name of the field that violated the unique constraint.
     * @param value      The double value that violated the unique constraint.
     */
    public UniqueConstraintViolationException(String errorCode, String fieldName, double value) {
        super(errorCode, new Object[]{value, fieldName});
    }

    /**
     * Constructor for UniqueConstraintViolationException with String array value type.
     *
     * @param errorCode  The error code to be used for this exception.
     * @param values     The string array value that violated the unique constraint.
     */
    public UniqueConstraintViolationException(String errorCode, String[] values) {
        super(errorCode, new Object[]{values});
    }
}
