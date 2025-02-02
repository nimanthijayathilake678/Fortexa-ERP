package com.shop.microservices.product.Exception;

import com.mongodb.MongoException;
import com.shop.microservices.product.Utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler is a centralized exception handler for the entire Spring MVC application.
 * It handles various exceptions thrown by controllers and creates consistent error responses.
 * This class leverages {@link ControllerAdvice} to globally handle exceptions and provide
 * structured error messages based on application-specific error codes and messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMessageUtil errorMessageUtil;

    @Autowired
    public GlobalExceptionHandler(ErrorMessageUtil errorMessageUtil) {
        this.errorMessageUtil = errorMessageUtil;
    }

    /**
     * Handles EntityCreationException thrown during the creation of an entity.
     * <p>
     * This exception is thrown when an error occurs during the creation of an entity,
     * and the handler ensures that a detailed error message is returned to the client.
     * </p>
     *
     * @param ex The {@link EntityCreationException} instance containing the details of the exception.
     * @return A {@link ResponseEntity} with a map containing error details, including the timestamp,
     *         status, message, and exception details.
     */
    @ExceptionHandler(EntityCreationException.class)
    public ResponseEntity<Map<String, Object>> handleEntityCreationException(EntityCreationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode(), ex.getMessageArgs()));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles FieldValidationException thrown when a field validation fails.
     * <p>
     * This exception is thrown when there is a problem with validating the fields of an entity.
     * The handler provides a clear error message to the client along with the details of the validation issue.
     * </p>
     *
     * @param ex The {@link FieldValidationException} instance containing the details of the exception.
     * @return A {@link ResponseEntity} with a map containing error details, including the timestamp,
     *         status, message, and exception details.
     */
    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<Map<String, Object>> handleFieldValidationException(FieldValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode()));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UniqueConstraintViolationException thrown when a unique constraint violation occurs.
     * <p>
     * This exception is thrown when a database constraint violation occurs, typically due to duplicate entries
     * in fields that should have unique values. The handler returns a structured error message to the client.
     * </p>
     *
     * @param ex The {@link UniqueConstraintViolationException} instance containing the details of the exception.
     * @return A {@link ResponseEntity} with a map containing error details, including the timestamp,
     *         status, message, and exception details.
     */
    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode(), ex.getMessageArgs()));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for {@link ResourceNotFoundException}.
     * This method captures the exception and constructs a user-friendly error response.
     *
     * @param ex The {@link ResourceNotFoundException} thrown when a requested resource is not found.
     * @return A {@link ResponseEntity} containing the error response as a map with relevant details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode(), ex.getMessageArgs()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for {@link InvalidInputException}.
     * This method captures the exception and constructs a user-friendly error response.
     *
     * @param ex The {@link InvalidInputException} thrown when invalid input is provided to the application.
     * @return A {@link ResponseEntity} containing the error response as a map with relevant details.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInputException(InvalidInputException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode(), ex.getMessageArgs()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ApplicationException thrown for general application-specific errors.
     * <p>
     * This exception is typically used for errors that are application-related but are not captured by more specific
     * exception types. The handler ensures a detailed message is returned to the client.
     * </p>
     *
     * @param ex The {@link ApplicationException} instance containing the details of the exception.
     * @return A {@link ResponseEntity} with a map containing error details, including the timestamp,
     *         status, message, and exception details.
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApplicationException(ApplicationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessageUtil.getErrorMessage(ex.getErrorCode(), ex.getMessageArgs()));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation errors in request bodies annotated with {@link javax.validation.Valid}.
     * <p>
     * This method is triggered when an HTTP request fails validation due to invalid input data.
     * The handler collects and returns field-specific validation errors with clear messages for the client.
     * </p>
     *
     * @param ex The {@link MethodArgumentNotValidException} that contains validation errors.
     * @return A {@link ResponseEntity} containing the validation errors in a {@link Map} format, with status code 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");

        // Extract field-specific validation errors
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        response.put("errors", fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from path or query parameters annotated with {@link javax.validation.Valid}.
     * <p>
     * This method is triggered when validation errors occur due to invalid parameters in the URL or query string.
     * The handler collects the violations and returns them in a structured response.
     * </p>
     *
     * @param ex The {@link ConstraintViolationException} that contains parameter-specific validation errors.
     * @return A {@link ResponseEntity} containing the validation violations in a {@link Map} format, with status code 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");

        // Extract parameter-specific validation errors
        Map<String, String> violations = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        response.put("errors", violations);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions related to MongoDB operations.
     * <p>
     * This method catches {@link com.mongodb.MongoException} and returns a standardized error response
     * to the client with details such as timestamp, HTTP status, a user-friendly error message,
     * and exception-specific details for debugging purposes.
     * </p>
     *
     * @param ex      the {@link MongoException} instance containing details about the database error.
     * @param request the {@link WebRequest} during which the exception occurred.
     * @return a {@link ResponseEntity} containing the error details in a {@link Map} format,
     *         with an HTTP status code of 500 (Internal Server Error).
     */
    @ExceptionHandler(MongoException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(MongoException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", errorMessageUtil.getErrorMessage("prod.error.3000"));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other generic exceptions that do not fall under specific exception types.
     * <p>
     * This method is used to catch unhandled exceptions and provide a generic error response to the client.
     * </p>
     *
     * @param ex      The {@link Exception} instance containing details about the exception.
     * @param request The {@link WebRequest} that triggered the exception.
     * @return A {@link ResponseEntity} containing the generic error details in a {@link Map} format, with status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", errorMessageUtil.getErrorMessage("generic.error.message"));
        response.put("details", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
