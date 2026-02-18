-- Seed data para ambiente de desenvolvimento
-- Esta migration repetível (R__) só é aplicada quando o perfil 'dev' está ativo

INSERT INTO evento_pedido (data_pedido, valor_pedido, status, nome_cliente) VALUES
    ('2025-01-10 10:30:00', 150.00, 'CONCLUIDO', 'João Silva'),
    ('2025-01-11 11:00:00', 89.90, 'CONCLUIDO', 'Maria Oliveira'),
    ('2025-01-12 14:15:00', 250.50, 'AGUARDANDO_PAGAMENTO', NULL),
    ('2025-01-13 09:45:00', 45.00, 'EM_PREPARO', 'Carlos Santos'),
    ('2025-01-14 16:20:00', 320.75, 'CONCLUIDO', NULL),
    ('2025-01-15 12:00:00', 75.00, 'CANCELADO', 'Ana Costa'),
    ('2025-01-16 18:30:00', 199.99, 'SAIU_PARA_ENTREGA', 'Pedro Lima')
ON CONFLICT DO NOTHING;
