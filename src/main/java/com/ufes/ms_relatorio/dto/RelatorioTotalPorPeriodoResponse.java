package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RelatorioTotalPorPeriodoResponse", description = "Resumo do total de pedidos concluídos por período")
public class RelatorioTotalPorPeriodoResponse {
    @Schema(description = "Data de início do período", example = "2026-02-01")
    private LocalDate periodoInicio;

    @Schema(description = "Data de fim do período", example = "2026-02-28")
    private LocalDate periodoFim;

    @Schema(description = "Valor total no período", example = "15432.80")
    private BigDecimal valorTotal;

    @Schema(description = "Quantidade de pedidos no período", example = "187")
    private Long quantidadePedidos;
}
