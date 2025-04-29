-- plural as the 'order' is reserved word
CREATE TABLE orders
(
    id          CHAR(36) PRIMARY KEY,
    customer_id CHAR(36)       NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_item
(
    id               CHAR(36) PRIMARY KEY,
    order_id         CHAR(36)       NOT NULL,
    book_id          CHAR(36)       NOT NULL,
    unit_price       DECIMAL(10, 2) NOT NULL,
    discounted_price DECIMAL(10, 2) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);