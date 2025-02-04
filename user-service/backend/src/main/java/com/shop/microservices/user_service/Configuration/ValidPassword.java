package com.shop.microservices.user_service.Configuration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation for validating password complexity.
 * <p>
 * This annotation can be applied to fields, methods, parameters, and other elements to enforce
 * password complexity rules. The password must include at least one capital letter, one lowercase letter,
 * one number, and one special character.
 * </p>
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    /**
     * The default message to be used when the password does not meet the complexity requirements.
     *
     * @return the error message
     */
    String message() default "Password must include at least one capital letter, one lowercase letter, one number, and one special character";

    /**
     * Allows the specification of validation groups, to which this constraint belongs.
     *
     * @return the array of classes representing the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     *
     * @return the array of classes representing the payload
     */
    Class<? extends Payload>[] payload() default {};
}