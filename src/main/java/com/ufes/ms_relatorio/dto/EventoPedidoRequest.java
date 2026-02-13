package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoPedidoRequest {

  @NotNull(message = "dataPedido é obrigatório")
  private LocalDateTime dataPedido;

  @NotNull(message = "valorPedido é obrigatório")
  @Positive(message = "valorPedido deve ser maior que zero")
  private BigDecimal valorPedido;

  @NotNull(message = "status é obrigatório")
  private StatusPedido status;
}
