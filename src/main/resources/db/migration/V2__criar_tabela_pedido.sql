CREATE TABLE pedido (
    id_pedido BIGSERIAL PRIMARY KEY,
    nome_cliente VARCHAR(255) NOT NULL,
    endereco_cliente VARCHAR(500) NOT NULL,
    forma_de_pagamento VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    valor_total NUMERIC(19, 2) NOT NULL,
    taxa_servico NUMERIC(19, 2),
    cupom VARCHAR(50),
    valor_desconto NUMERIC(19, 2),
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
