package com.ufes.ms_relatorio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_produto")
  private Long idProduto;

  @Column(name = "nome_produto")
  private String nomeProduto;

  @Column(name = "quantidade")
  private Integer quantidade;

  @Column(name = "valor_unidade", precision = 19, scale = 2)
  private BigDecimal valorUnidade;

  @Column(name = "observacao")
  private String observacao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "evento_pedido_id")
  private EventoPedido eventoPedido;
}
