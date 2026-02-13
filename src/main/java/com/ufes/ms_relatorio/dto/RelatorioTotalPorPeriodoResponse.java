package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.TipoPeriodo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioTotalPorPeriodoResponse {
    private List<TotalPorPeriodoItemDTO> itens;
    private PeriodoDTO periodoConsulta;
    private TipoPeriodo tipoPeriodo;
}
