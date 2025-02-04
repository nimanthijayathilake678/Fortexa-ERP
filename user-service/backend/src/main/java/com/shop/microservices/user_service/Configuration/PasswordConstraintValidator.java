package com.shop.microservices.user_service.Configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator class for validating password complexity.
 * <p>
 * This class implements the {@link ConstraintValidator} interface to provide custom validation logic
 * for the {@link ValidPassword} annotation. The password must include at least one capital letter,
 * one lowercase letter, one number, and one special character.
 * </p>
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Initializes the validator in preparation for {@link #isValid(String, ConstraintValidatorContext)} calls.
     * This method is a no-op in this implementation.
     *
     * @param constraintAnnotation the annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    /**
     * Implements the validation logic.
     * <p>
     * The password must include at least one capital letter, one lowercase letter, one number, and one special character.
     * The length of the password must be between 8 and 32 characters.
     * </p>
     *
     * @param password the password to validate
     * @param context  context in which the constraint is evaluated
     * @return {@code true} if the password is valid, {@code false} otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        // Password must include at least one capital letter, one lowercase letter, one number, and one special character
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,32}$");
    }
}