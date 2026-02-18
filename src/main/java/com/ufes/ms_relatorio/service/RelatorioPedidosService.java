package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.entity.StatusPedido;
import org.springframework.data.domain.Page;

public interface RelatorioPedidosService {
  Page<EventoPedidoResponse> listarPedidos(Periodo periodo, StatusPedido status, int page, int size);
}
