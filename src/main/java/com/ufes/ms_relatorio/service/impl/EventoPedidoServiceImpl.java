package com.ufes.ms_relatorio.service.impl;

import com.ufes.ms_relatorio.dto.EventoPedidoRequest;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.dto.UpsertResult;
import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.mapper.EventoPedidoMapper;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.service.EventoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoPedidoServiceImpl implements EventoPedidoService {

  private final EventoPedidoRepository eventoPedidoRepository;
  private final EventoPedidoMapper eventoPedidoMapper;

  @Override
  @Transactional
  public UpsertResult<EventoPedidoResponse> salvar(EventoPedidoRequest request) {
    if (request.getId() != null) {
      Optional<EventoPedido> existente = eventoPedidoRepository.findById(request.getId());

      if (existente.isPresent()) {
        EventoPedido pedido = existente.get();
        pedido.setDataPedido(request.getDataPedido());
        pedido.setValorPedido(request.getValorPedido());
        pedido.setStatus(request.getStatus());
        pedido.setNomeCliente(request.getNomeCliente());
        pedido.adicionarItens(eventoPedidoMapper.toEntityList(request.getItens()));

        EventoPedido salvo = eventoPedidoRepository.save(pedido);
        return new UpsertResult<>(eventoPedidoMapper.toResponse(salvo), false);
      }
    }

    EventoPedido novo = EventoPedido.builder()
        .dataPedido(request.getDataPedido())
        .valorPedido(request.getValorPedido())
        .status(request.getStatus())
        .nomeCliente(request.getNomeCliente())
        .build();

    novo.adicionarItens(eventoPedidoMapper.toEntityList(request.getItens()));

    EventoPedido salvo = eventoPedidoRepository.save(novo);
    return new UpsertResult<>(eventoPedidoMapper.toResponse(salvo), true);
  }
}
