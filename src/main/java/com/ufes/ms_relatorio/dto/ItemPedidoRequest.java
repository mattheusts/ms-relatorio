package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemPedidoRequest", description = "Dados de entrada de um item de pedido")
public class ItemPedidoRequest {

  @Schema(description = "Identificador do produto", example = "101")
  private Long idProduto;

  @Schema(description = "Nome do produto", example = "Pizza Calabresa Grande")
  private String nomeProduto;

  @Schema(description = "Quantidade solicitada", example = "2")
  private Integer quantidade;

  @Schema(description = "Valor unitário do item", example = "44.95")
  private BigDecimal valorUnidade;

  @Schema(description = "Observação do item", example = "Sem cebola")
  private String observacao;
}
