package com.shop.microservices.product.Exception;

/**
 * Exception thrown when invalid input is provided to the application.
 * <p>
 * This class extends the {@link ApplicationException} to represent specific cases
 * where the input provided by the client or user is invalid. It includes
 * an error code that can be used for consistent error handling and debugging.
 * </p>
 * <p>
 * Usage:
 * <ul>
 *     <li>Throw this exception when validating input parameters and encountering invalid data.</li>
 *     <li>The error code provides context about the specific type of input validation failure.</li>
 * </ul>
 * </p>
 * <p>
 * Example:
 * <pre>{@code
 * if (input == null || input.isEmpty()) {
 *     throw new InvalidInputException("input.error.nullOrEmpty");
 * }
 * }</pre>
 * </p>
 *
 * @see ApplicationException
 */
public class InvalidInputException extends ApplicationException {

    /**
     * Constructs a new InvalidInputException with the specified error code.
     * <p>
     * The error code can be used to identify the specific type of input validation failure
     * and can be mapped to user-friendly error messages or logs.
     * </p>
     *
     * @param errorCode A string representing the error code (e.g., "input.error.invalidFormat").
     */
    public InvalidInputException(String errorCode) {
        super(errorCode, null);
    }
}

