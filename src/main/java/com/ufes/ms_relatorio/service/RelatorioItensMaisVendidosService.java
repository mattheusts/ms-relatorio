package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.RelatorioItemMaisVendidoResponse;

import java.util.List;

public interface RelatorioItensMaisVendidosService {
  List<RelatorioItemMaisVendidoResponse> listarItensMaisVendidos(Periodo periodo, int quantidade);
}
