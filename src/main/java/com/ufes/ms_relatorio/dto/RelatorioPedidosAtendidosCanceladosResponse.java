package com.ufes.ms_relatorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RelatorioPedidosAtendidosCanceladosResponse", description = "Consolidado de pedidos atendidos e cancelados no período")
public class RelatorioPedidosAtendidosCanceladosResponse {

    @Schema(description = "Início do período analisado", example = "2026-02-01T00:00:00")
    private LocalDateTime dataInicio;

    @Schema(description = "Fim do período analisado", example = "2026-02-28T23:59:59")
    private LocalDateTime dataFim;

    @Schema(description = "Quantidade de pedidos atendidos", example = "120")
    private long quantidadeAtendidos;

    @Schema(description = "Quantidade de pedidos cancelados", example = "18")
    private long quantidadeCancelados;

    @Schema(description = "Diferença entre atendidos e cancelados", example = "102")
    private long saldo;

    @Schema(description = "Valor total dos pedidos atendidos", example = "15432.80")
    private BigDecimal saldoTotalAtendidos;
}
