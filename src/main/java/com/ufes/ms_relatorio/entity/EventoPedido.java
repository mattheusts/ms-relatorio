package com.ufes.ms_relatorio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
}
