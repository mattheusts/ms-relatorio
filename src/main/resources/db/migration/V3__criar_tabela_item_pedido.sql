CREATE TABLE item_pedido (
    id BIGSERIAL PRIMARY KEY,
    id_pedido BIGINT NOT NULL REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    id_produto BIGINT NOT NULL REFERENCES cardapio(id_produto),
    quantidade INTEGER NOT NULL,
    valor_unidade NUMERIC(19, 2) NOT NULL,
    observacao VARCHAR(500)
);

CREATE INDEX idx_item_pedido_id_pedido ON item_pedido(id_pedido);
CREATE INDEX idx_item_pedido_id_produto ON item_pedido(id_produto);
CREATE INDEX idx_pedido_data_status ON pedido(data_pedido, status);
