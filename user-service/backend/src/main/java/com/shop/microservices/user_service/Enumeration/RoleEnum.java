package com.shop.microservices.user_service.Enumeration;

/**
 * Enum representing the various roles in the system.
 * Each role defines a specific level of access and responsibility within the organization or application.
 * Roles are typically assigned to users to manage their permissions and access to different resources.
 *
 * <p>
 * The roles defined in this enum cover a broad range of user responsibilities, from administrative duties
 * (e.g., {@link #ADMIN}, {@link #OWNER}) to more specific functions such as inventory management
 * (e.g., {@link #INVENTORY_MANAGER}), marketing, accounting, and sales roles.
 * </p>
 *
 * <p>
 * This enum is used to assign and manage the access control of users in the application, allowing
 * for role-based access control (RBAC).
 * </p>
 */
public enum RoleEnum {
    /**
     * The highest level of access with full control over all resources in the system.
     */
    ADMIN,

    /**
     * The owner of the application, typically has the same level of access as an admin.
     */
    OWNER,

    /**
     * A role with elevated access, typically with management responsibilities for all aspects of the system.
     */
    SUPER_USER,

    /**
     * General manager with overall authority in managing company operations.
     */
    GENERAL_MANAGER,

    /**
     * Responsible for overseeing product-related functions, such as product creation and management.
     */
    PRODUCT_MANAGER,

    /**
     * Manages the company's inventory, ensuring proper stock levels and management.
     */
    INVENTORY_MANAGER,

    /**
     * Handles marketing activities including promotions, advertisements, and customer engagement.
     */
    MARKETING_MANAGER,

    /**
     * Manages sales operations and ensures revenue generation targets are met.
     */
    SALES_MANAGER,

    /**
     * Responsible for handling financial transactions, reports, and maintaining financial records.
     */
    ACCOUNTANT,

    /**
     * The cashier role handles monetary transactions and customer payments.
     */
    CASHIER,

    /**
     * Logistic staff are responsible for managing transportation, deliveries, and inventory flow.
     */
    LOGISTIC_STAFF,

    /**
     * Handles the analysis of data within the organization to support decision-making processes.
     */
    DATA_ANALYST,

    /**
     * Manages warehouse operations, including stock management and fulfillment.
     */
    WAREHOUSE_STAFF,

    /**
     * Human resources manager responsible for employee relations, hiring, and maintaining HR records.
     */
    HR_MANAGER,

    /**
     * QA (Quality Assurance) is responsible for ensuring the quality of products and services, conducting testing, and identifying improvements.
     */
    QA
}
