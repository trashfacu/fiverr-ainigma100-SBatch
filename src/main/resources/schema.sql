-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE ACCOUNT_VIGI
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id    BIGINT,
    account_number VARCHAR(255),
    balance        DECIMAL,
    execution_date TIMESTAMP
);

CREATE TABLE CUSTOMER_VIGI
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255),
    email          VARCHAR(255),
    country_code   VARCHAR(10),
    execution_date TIMESTAMP
);

CREATE TABLE INVALID_RECORDS
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    record_type    VARCHAR(255),
    record_content VARCHAR(255),
    error_message  VARCHAR(255)
);