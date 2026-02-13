package com.ufes.ms_relatorio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

    @Column(name = "endereco_cliente", nullable = false, length = 500)
    private String enderecoCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_de_pagamento", nullable = false)
    private FormaDePagamento formaDePagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido status;

    @Column(name = "valor_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "taxa_servico", precision = 19, scale = 2)
    private BigDecimal taxaServico;

    @Column(name = "cupom", length = 50)
    private String cupom;

    @Column(name = "valor_desconto", precision = 19, scale = 2)
    private BigDecimal valorDesconto;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedido> itens = new ArrayList<>();
}
