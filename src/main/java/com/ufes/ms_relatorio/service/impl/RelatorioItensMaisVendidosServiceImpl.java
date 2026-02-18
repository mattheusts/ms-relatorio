package com.ufes.ms_relatorio.service.impl;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.RelatorioItemMaisVendidoResponse;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.service.RelatorioItensMaisVendidosService;
import com.ufes.ms_relatorio.strategy.CriterioVendaConcluida;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioItensMaisVendidosServiceImpl implements RelatorioItensMaisVendidosService {

  private final EventoPedidoRepository eventoPedidoRepository;
  private final CriterioVendaConcluida criterioVendaConcluida;

  @Override
  @Transactional(readOnly = true)
  public List<RelatorioItemMaisVendidoResponse> listarItensMaisVendidos(Periodo periodo, int quantidade) {
    if (quantidade <= 0) {
      throw new IllegalArgumentException("quantidade deve ser maior que zero");
    }

    List<StatusPedido> statusList = criterioVendaConcluida.obterStatusConcluidos();
    if (statusList == null || statusList.isEmpty()) {
      statusList = List.of(StatusPedido.CONCLUIDO);
    }

    Pageable limite = PageRequest.of(0, quantidade);

    return eventoPedidoRepository.buscarItensMaisVendidos(
        periodo.dataInicio(), periodo.dataFim(), statusList, limite);
  }
}
