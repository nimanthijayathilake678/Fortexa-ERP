-- src/main/resources/db/migration/V1__Create_user_service_tables.sql

USE user_service_db;

-- Create table for PersonalInfo
CREATE TABLE IF NOT EXISTS personal_info (
    id CHAR(36) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    nic VARCHAR(255) NOT NULL UNIQUE,
    picture_id INT,
    address VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create table for Role
CREATE TABLE IF NOT EXISTS role (
    id CHAR(36) NOT NULL,
    role VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create table for Permission
CREATE TABLE IF NOT EXISTS permission (
    id CHAR(36) NOT NULL,
    permission VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create table for User
CREATE TABLE IF NOT EXISTS user (
    id CHAR(36) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile_no VARCHAR(255) UNIQUE,
    status VARCHAR(255) NOT NULL,
    two_factor_enabled BOOLEAN NOT NULL,
    personal_info_id CHAR(36),
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (personal_info_id) REFERENCES personal_info(id)
);

-- Create table for RolePermission
CREATE TABLE IF NOT EXISTS role_permissions (
    id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    permission_id CHAR(36) NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);

-- Create table for UserRole
CREATE TABLE IF NOT EXISTS user_roles (
    id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Indexes for performance optimization
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_role_role ON role(role);
CREATE INDEX idx_permission_permission ON permission(permission);
CREATE INDEX idx_personal_info_nic ON personal_info(nic);