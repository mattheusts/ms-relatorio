package com.ufes.ms_relatorio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "data_pedido", nullable = false)
  private LocalDateTime dataPedido;

  @Column(name = "valor_pedido", nullable = false, precision = 19, scale = 2)
  private BigDecimal valorPedido;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private StatusPedido status;

  @Column(name = "nome_cliente")
  private String nomeCliente;

  @OneToMany(mappedBy = "eventoPedido", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ItemPedido> itens = new ArrayList<>();

  public void adicionarItens(List<ItemPedido> novosItens) {
    this.itens.clear();
    if (novosItens != null) {
      novosItens.forEach(item -> item.setEventoPedido(this));
      this.itens.addAll(novosItens);
    }
  }
}
