package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoPedidoResponse {

  private Long id;
  private LocalDateTime dataPedido;
  private BigDecimal valorPedido;
  private StatusPedido status;
  private String nomeCliente;
  private List<ItemPedidoResponse> itens;
}
