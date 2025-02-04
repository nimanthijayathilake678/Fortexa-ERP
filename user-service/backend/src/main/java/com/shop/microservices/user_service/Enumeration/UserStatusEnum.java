package com.shop.microservices.user_service.Enumeration;

/**
 * Enum representing the status of a user in the system.
 *
 * <p>
 * The {@link #ACTIVE} status allows access to system features, while {@link #INACTIVE}
 * indicates the user is active or Inactive.
 * </p>
 */
public enum UserStatusEnum {
    /**
     * User is active and has access to the system.
     */
    ACTIVE,

    /**
     * User is inactive and does not have access to the system.
     */
    INACTIVE
}
