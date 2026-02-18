package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.RelatorioPedidosAtendidosCanceladosResponse;

public interface RelatorioPedidosAtendidosCanceladosService {
    RelatorioPedidosAtendidosCanceladosResponse gerar(Periodo periodo);
}
