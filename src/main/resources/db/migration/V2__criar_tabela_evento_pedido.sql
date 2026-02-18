CREATE TABLE evento_pedido (
    id BIGSERIAL PRIMARY KEY,
    data_pedido TIMESTAMP NOT NULL,
    valor_pedido DECIMAL(19, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    nome_cliente VARCHAR(255)
);

CREATE TABLE item_pedido (
    id BIGSERIAL PRIMARY KEY,
    evento_pedido_id BIGINT NOT NULL,
    id_produto BIGINT,
    nome_produto VARCHAR(255),
    quantidade INTEGER,
    valor_unidade DECIMAL(19, 2),
    observacao VARCHAR(500),
    CONSTRAINT fk_item_pedido_evento FOREIGN KEY (evento_pedido_id) REFERENCES evento_pedido(id) ON DELETE CASCADE
);

CREATE INDEX idx_evento_pedido_data_pedido ON evento_pedido (data_pedido);
CREATE INDEX idx_evento_pedido_status ON evento_pedido (status);
CREATE INDEX idx_item_pedido_evento_pedido_id ON item_pedido (evento_pedido_id);
