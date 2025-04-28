CREATE TABLE book
(
    id             CHAR(36) PRIMARY KEY,
    title          VARCHAR(255)   NOT NULL,
    author         VARCHAR(255)   NOT NULL,
    base_price     DECIMAL(10, 2) NOT NULL,
    type           VARCHAR(255)   NOT NULL,
    stock_quantity INT       DEFAULT 0,
    available      BOOLEAN   DEFAULT FALSE,
    active         BOOLEAN   DEFAULT TRUE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);