package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.EventoPedidoRequest;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.dto.UpsertResult;
import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoPedidoService {

  private final EventoPedidoRepository eventoPedidoRepository;

  @Transactional
  public UpsertResult<EventoPedidoResponse> salvar(EventoPedidoRequest request) {
    if (request.getId() != null) {
      Optional<EventoPedido> existente = eventoPedidoRepository.findById(request.getId());

      if (existente.isPresent()) {
        EventoPedido pedido = existente.get();
        pedido.setDataPedido(request.getDataPedido());
        pedido.setValorPedido(request.getValorPedido());
        pedido.setStatus(request.getStatus());

        EventoPedido salvo = eventoPedidoRepository.save(pedido);
        return new UpsertResult<>(toResponse(salvo), false);
      }
    }

    EventoPedido novo = EventoPedido.builder()
        .dataPedido(request.getDataPedido())
        .valorPedido(request.getValorPedido())
        .status(request.getStatus())
        .build();

    EventoPedido salvo = eventoPedidoRepository.save(novo);
    return new UpsertResult<>(toResponse(salvo), true);
  }

  @Transactional(readOnly = true)
  public Page<EventoPedidoResponse> listarPedidos(
      LocalDateTime dataInicio,
      LocalDateTime dataFim,
      StatusPedido status,
      int page,
      int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("dataPedido").ascending());

    Page<EventoPedido> eventos;
    if (status != null) {
      eventos = eventoPedidoRepository.findByDataPedidoBetweenAndStatus(dataInicio, dataFim, status, pageable);
    } else {
      eventos = eventoPedidoRepository.findByDataPedidoBetween(dataInicio, dataFim, pageable);
    }

    return eventos.map(this::toResponse);
  }

  @Transactional(readOnly = true)
  public BigDecimal calcularTotalVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
    return eventoPedidoRepository.somarVendasConcluidas(dataInicio, dataFim);
  }

  private EventoPedidoResponse toResponse(EventoPedido evento) {
    return EventoPedidoResponse.builder()
        .id(evento.getId())
        .dataPedido(evento.getDataPedido())
        .valorPedido(evento.getValorPedido())
        .status(evento.getStatus())
        .build();
  }
}
