CREATE TABLE evento_pedido (
    id BIGSERIAL PRIMARY KEY,
    data_pedido TIMESTAMP NOT NULL,
    valor_pedido DECIMAL(19, 2) NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_evento_pedido_data_pedido ON evento_pedido (data_pedido);
CREATE INDEX idx_evento_pedido_status ON evento_pedido (status);

INSERT INTO evento_pedido (data_pedido, valor_pedido, status) VALUES
    ('2025-01-10 10:30:00', 150.00, 'CONCLUIDO'),
    ('2025-01-11 11:00:00', 89.90, 'CONCLUIDO'),
    ('2025-01-12 14:15:00', 250.50, 'AGUARDANDO_PAGAMENTO'),
    ('2025-01-13 09:45:00', 45.00, 'EM_PREPARO'),
    ('2025-01-14 16:20:00', 320.75, 'CONCLUIDO'),
    ('2025-01-15 12:00:00', 75.00, 'CANCELADO'),
    ('2025-01-16 18:30:00', 199.99, 'SAIU_PARA_ENTREGA');
