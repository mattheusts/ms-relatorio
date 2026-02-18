package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "EventoPedidoResponse", description = "Dados de saída de um pedido")
public class EventoPedidoResponse {

  @Schema(description = "Identificador do pedido", example = "1001")
  private Long id;

  @Schema(description = "Data e hora do pedido", example = "2026-02-18T19:30:00")
  private LocalDateTime dataPedido;

  @Schema(description = "Valor total do pedido", example = "89.90")
  private BigDecimal valorPedido;

  @Schema(description = "Status atual do pedido", example = "CONCLUIDO")
  private StatusPedido status;

  @Schema(description = "Nome do cliente", example = "Maria Oliveira")
  private String nomeCliente;

  @Schema(description = "Itens associados ao pedido")
  private List<ItemPedidoResponse> itens;
}
