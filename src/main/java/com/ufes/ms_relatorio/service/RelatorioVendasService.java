package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.domain.Periodo;

import java.math.BigDecimal;

public interface RelatorioVendasService {
  BigDecimal calcularTotalVendas(Periodo periodo);
}
