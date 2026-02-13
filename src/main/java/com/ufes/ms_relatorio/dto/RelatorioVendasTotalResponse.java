package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioVendasTotalResponse {

  private BigDecimal totalVendas;
}
