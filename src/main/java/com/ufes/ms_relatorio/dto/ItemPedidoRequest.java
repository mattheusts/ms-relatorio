package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemPedidoRequest", description = "Dados de entrada de um item de pedido")
public class ItemPedidoRequest {

  @NotNull(message = "idProduto é obrigatório")
  @Schema(description = "Identificador do produto", example = "101")
  private Long idProduto;

  @NotNull(message = "nomeProduto é obrigatório")
  @Schema(description = "Nome do produto", example = "Pizza Calabresa Grande")
  private String nomeProduto;

  @NotNull(message = "quantidade é obrigatória")
  @Positive(message = "quantidade deve ser maior que zero")
  @Schema(description = "Quantidade solicitada", example = "2")
  private Integer quantidade;

  @NotNull(message = "valorUnidade é obrigatório")
  @Positive(message = "valorUnidade deve ser maior que zero")
  @Schema(description = "Valor unitário do item", example = "44.95")
  private BigDecimal valorUnidade;

  @Schema(description = "Observação do item", example = "Sem cebola")
  private String observacao;
}
