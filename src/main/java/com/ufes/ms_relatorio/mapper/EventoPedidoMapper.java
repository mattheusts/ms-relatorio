package com.ufes.ms_relatorio.mapper;

import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.dto.ItemPedidoRequest;
import com.ufes.ms_relatorio.dto.ItemPedidoResponse;
import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.entity.ItemPedido;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventoPedidoMapper {

  public EventoPedidoResponse toResponse(EventoPedido evento) {
    List<ItemPedidoResponse> itensResponse = evento.getItens() != null
        ? evento.getItens().stream()
            .map(this::toItemResponse)
            .collect(Collectors.toList())
        : Collections.emptyList();

    return EventoPedidoResponse.builder()
        .id(evento.getId())
        .dataPedido(evento.getDataPedido())
        .valorPedido(evento.getValorPedido())
        .status(evento.getStatus())
        .nomeCliente(evento.getNomeCliente())
        .itens(itensResponse)
        .build();
  }

  public List<ItemPedido> toEntityList(List<ItemPedidoRequest> itensRequest) {
    if (itensRequest == null || itensRequest.isEmpty()) {
      return Collections.emptyList();
    }
    return itensRequest.stream()
        .map(this::toEntity)
        .collect(Collectors.toList());
  }

  private ItemPedidoResponse toItemResponse(ItemPedido item) {
    return ItemPedidoResponse.builder()
        .id(item.getId())
        .idProduto(item.getIdProduto())
        .nomeProduto(item.getNomeProduto())
        .quantidade(item.getQuantidade())
        .valorUnidade(item.getValorUnidade())
        .observacao(item.getObservacao())
        .build();
  }

  private ItemPedido toEntity(ItemPedidoRequest item) {
    return ItemPedido.builder()
        .idProduto(item.getIdProduto())
        .nomeProduto(item.getNomeProduto())
        .quantidade(item.getQuantidade())
        .valorUnidade(item.getValorUnidade())
        .observacao(item.getObservacao())
        .build();
  }
}
