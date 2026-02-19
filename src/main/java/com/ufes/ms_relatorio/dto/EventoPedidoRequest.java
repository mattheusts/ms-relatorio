package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "EventoPedidoRequest", description = "Dados de entrada para criação/atualização de um pedido")
public class EventoPedidoRequest {

  @Schema(description = "ID do pedido para atualização (opcional)", example = "1001")
  private Long id;

  @NotNull(message = "dataPedido é obrigatório")
  @Schema(description = "Data e hora do pedido", example = "2026-02-18T19:30:00")
  private LocalDateTime dataPedido;

  @NotNull(message = "valorDesconto é obrigatório")
  @DecimalMin(value = "0.00", inclusive = true, message = "valorDesconto deve ser maior ou igual a zero")
  @Schema(description = "Valor de desconto aplicado ao pedido", example = "10.00")
  private BigDecimal valorDesconto;

  @NotNull(message = "status é obrigatório")
  @Schema(description = "Status atual do pedido", example = "AGUARDANDO_PAGAMENTO")
  private StatusPedido status;

  @Schema(description = "Nome do cliente", example = "Maria Oliveira")
  private String nomeCliente;

  @Builder.Default
  @Schema(description = "Lista de itens do pedido")
  @Valid
  private List<ItemPedidoRequest> itens = new ArrayList<>();
}
