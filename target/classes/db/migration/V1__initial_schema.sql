CREATE TABLE customer_download (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_id BIGINT,
    name VARCHAR(255) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    size_bytes BIGINT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_customer_download_customer ON customer_download(customer_id);
CREATE INDEX idx_customer_download_created_at ON customer_download(created_at DESC);
