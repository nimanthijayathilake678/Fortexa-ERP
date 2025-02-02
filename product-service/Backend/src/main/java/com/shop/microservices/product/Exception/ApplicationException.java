package com.shop.microservices.product.Exception;

/**
 * Base exception for the application.
 * <p>
 * This class serves as the root exception for all custom exceptions within the application.
 * It provides a structure for capturing error codes and additional message arguments,
 * enabling consistent error reporting and handling throughout the system.
 * </p>
 * <p>
 * Usage:
 * <ul>
 *     <li>All custom exceptions should extend this class to ensure consistency.</li>
 *     <li>Error codes and additional arguments can be used to provide context for debugging and logging.</li>
 * </ul>
 * </p>
 */
public class ApplicationException extends RuntimeException {
    private final String errorCode;
    private final Object[] messageArgs;

    /**
     * Constructs a new ApplicationException with a specific error code, message arguments,
     * and the root cause of the exception.
     * <p>
     * This constructor is useful when the exception is caused by another lower-level exception.
     * </p>
     *
     * @param errorCode   A specific error code indicating the nature of the error (e.g., "app.error.1001").
     * @param messageArgs Additional arguments for error message formatting or context (e.g., entity names, IDs).
     * @param cause       The root cause of the exception, which can be used for debugging.
     */
    public ApplicationException(String errorCode, Object[] messageArgs, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    /**
     * Constructs a new ApplicationException with a specific error code and message arguments.
     * <p>
     * This constructor is useful for application-specific errors that are not caused by lower-level exceptions.
     * </p>
     *
     * @param errorCode   A specific error code indicating the nature of the error (e.g., "app.error.1002").
     * @param messageArgs Additional arguments for error message formatting or context (e.g., entity names, IDs).
     */
    public ApplicationException(String errorCode, Object[] messageArgs) {
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    /**
     * Retrieves the error code associated with this exception.
     * <p>
     * The error code provides a unique identifier for the type of error, which can be used
     * for logging, debugging, or user-friendly error messages.
     * </p>
     *
     * @return The error code as a string (e.g., "app.error.1001").
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Retrieves the additional message arguments associated with this exception.
     * <p>
     * These arguments can provide contextual details about the error, such as entity names, IDs,
     * or any other relevant information that helps understand the issue.
     * </p>
     *
     * @return An array of objects representing the additional message arguments, or {@code null} if none are provided.
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }
}
