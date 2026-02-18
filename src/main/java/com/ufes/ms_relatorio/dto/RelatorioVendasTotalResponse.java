package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RelatorioVendasTotalResponse", description = "Total de vendas concluídas no período")
public class RelatorioVendasTotalResponse {

  @Schema(description = "Valor total de vendas", example = "15432.80")
  private BigDecimal totalVendas;
}
