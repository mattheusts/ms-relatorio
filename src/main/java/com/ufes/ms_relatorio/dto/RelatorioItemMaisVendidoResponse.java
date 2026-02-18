package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "RelatorioItemMaisVendidoResponse", description = "Item consolidado no ranking de mais vendidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioItemMaisVendidoResponse {

  @Schema(description = "Identificador do produto", example = "101")
  private Long idProduto;

  @Schema(description = "Nome do produto", example = "Pizza Calabresa")
  private String nomeProduto;

  @Schema(description = "Quantidade total vendida no período", example = "42")
  private Long quantidadeVendida;
}
