package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.EventoPedidoRequest;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.dto.UpsertResult;

public interface EventoPedidoService {
  UpsertResult<EventoPedidoResponse> salvar(EventoPedidoRequest request);
}
