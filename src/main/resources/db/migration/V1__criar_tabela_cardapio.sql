CREATE TABLE cardapio (
    id_produto BIGSERIAL PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor NUMERIC(19, 2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true
);
