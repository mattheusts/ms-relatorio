package com.ufes.ms_relatorio.service.impl;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.mapper.EventoPedidoMapper;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.service.RelatorioPedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RelatorioPedidosServiceImpl implements RelatorioPedidosService {

  private final EventoPedidoRepository eventoPedidoRepository;
  private final EventoPedidoMapper eventoPedidoMapper;

  @Override
  @Transactional(readOnly = true)
  public Page<EventoPedidoResponse> listarPedidos(Periodo periodo, StatusPedido status, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("dataPedido").ascending());

    Page<EventoPedido> eventos;
    if (status != null) {
      eventos = eventoPedidoRepository.findByDataPedidoBetweenAndStatus(
          periodo.dataInicio(), periodo.dataFim(), status, pageable);
    } else {
      eventos = eventoPedidoRepository.findByDataPedidoBetween(
          periodo.dataInicio(), periodo.dataFim(), pageable);
    }

    return eventos.map(eventoPedidoMapper::toResponse);
  }
}
