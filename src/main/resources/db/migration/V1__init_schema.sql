CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        csv_id VARCHAR(255),
                        customer_name VARCHAR(255),
                        category VARCHAR(255),
                        price DOUBLE PRECISION NOT NULL,
                        order_date DATE,
                        is_completed BOOLEAN NOT NULL
);