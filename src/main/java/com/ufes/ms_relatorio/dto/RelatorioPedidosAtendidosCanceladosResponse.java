package com.ufes.ms_relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioPedidosAtendidosCanceladosResponse {

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private long quantidadeAtendidos;
    private long quantidadeCancelados;
    private long saldo;
}
