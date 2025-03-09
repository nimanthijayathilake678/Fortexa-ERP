package com.shop.microservices.product.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Utility class to fetch error messages from message properties.
 * Supports internationalization (i18n) for different locales.
 */

@Component
public class ErrorMessageUtil {

    // Injected MessageSource to retrieve messages from the property files
    private final MessageSource messageSource;

    // Constructor for dependency injection
    @Autowired
    public ErrorMessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Fetches the error message corresponding to the given code and arguments,
     * and includes the error code in the returned message.
     *
     * @param code The error message code to look up.
     * @param args The arguments to format the message (optional).
     * @return The formatted error message including the error code.
     */
    public String getErrorMessage(String code, Object[] args) {
        // Fetch the error message from the properties file
        String message = messageSource.getMessage(code, args, Locale.getDefault());
        // Format the message to include the error code
        return String.format("%s: %s", code, message);
    }

    /**
     * Fetches the error message corresponding to the given code,
     * and includes the error code in the returned message.
     *
     * @param code The error message code to look up.
     * @return The formatted error message including the error code.
     */
    public String getErrorMessage(String code) {
        return getErrorMessage(code, null);
    }

}
