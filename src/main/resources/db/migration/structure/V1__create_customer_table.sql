CREATE TABLE Customer
(
    id             CHAR(36) PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    loyalty_points INT       DEFAULT 0,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);