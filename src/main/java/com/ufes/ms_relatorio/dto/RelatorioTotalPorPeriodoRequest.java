package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.TipoPeriodo;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioTotalPorPeriodoRequest {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private TipoPeriodo tipoPeriodo;
}
