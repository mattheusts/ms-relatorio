package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.RelatorioTotalPorPeriodoRequest;
import com.ufes.ms_relatorio.dto.RelatorioTotalPorPeriodoResponse;

public interface RelatorioTotalPorPeriodoService {
    RelatorioTotalPorPeriodoResponse gerar(RelatorioTotalPorPeriodoRequest request);
}
