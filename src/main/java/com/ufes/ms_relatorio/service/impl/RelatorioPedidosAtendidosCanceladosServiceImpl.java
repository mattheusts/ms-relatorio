package com.ufes.ms_relatorio.service.impl;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.RelatorioPedidosAtendidosCanceladosResponse;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.service.RelatorioPedidosAtendidosCanceladosService;
import com.ufes.ms_relatorio.strategy.CriterioPedidoAtendidoCancelado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioPedidosAtendidosCanceladosServiceImpl implements RelatorioPedidosAtendidosCanceladosService {

  private final EventoPedidoRepository eventoPedidoRepository;
  private final CriterioPedidoAtendidoCancelado criterioPedidoAtendidoCancelado;

  @Override
  @Transactional(readOnly = true)
  public RelatorioPedidosAtendidosCanceladosResponse gerar(Periodo periodo) {
    List<StatusPedido> statusAtendidos = criterioPedidoAtendidoCancelado.obterStatusAtendidos();
    if (statusAtendidos == null || statusAtendidos.isEmpty()) {
      statusAtendidos = List.of(StatusPedido.CONCLUIDO);
    }

    List<StatusPedido> statusCancelados = criterioPedidoAtendidoCancelado.obterStatusCancelados();
    if (statusCancelados == null || statusCancelados.isEmpty()) {
      statusCancelados = List.of(StatusPedido.CANCELADO);
    }

    long quantidadeAtendidos = eventoPedidoRepository.countByDataPedidoBetweenAndStatusIn(
        periodo.dataInicio(), periodo.dataFim(), statusAtendidos);

    long quantidadeCancelados = eventoPedidoRepository.countByDataPedidoBetweenAndStatusIn(
        periodo.dataInicio(), periodo.dataFim(), statusCancelados);

    BigDecimal saldoTotalAtendidos = eventoPedidoRepository.somarValorPedidosPorStatus(
        periodo.dataInicio(), periodo.dataFim(), statusAtendidos);

    if (saldoTotalAtendidos == null) {
      saldoTotalAtendidos = BigDecimal.ZERO;
    }

    saldoTotalAtendidos = saldoTotalAtendidos.setScale(2, RoundingMode.HALF_UP);

    return RelatorioPedidosAtendidosCanceladosResponse.builder()
        .dataInicio(periodo.dataInicio())
        .dataFim(periodo.dataFim())
        .quantidadeAtendidos(quantidadeAtendidos)
        .quantidadeCancelados(quantidadeCancelados)
        .saldo(quantidadeAtendidos - quantidadeCancelados)
        .saldoTotalAtendidos(saldoTotalAtendidos)
        .build();
  }
}
