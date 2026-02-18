package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoPedidoRequest {

  private Long id;

  @NotNull(message = "dataPedido é obrigatório")
  private LocalDateTime dataPedido;

  @NotNull(message = "valorPedido é obrigatório")
  @Positive(message = "valorPedido deve ser maior que zero")
  private BigDecimal valorPedido;

  @NotNull(message = "status é obrigatório")
  private StatusPedido status;

  private String nomeCliente;

  @Builder.Default
  private List<ItemPedidoRequest> itens = new ArrayList<>();
}
