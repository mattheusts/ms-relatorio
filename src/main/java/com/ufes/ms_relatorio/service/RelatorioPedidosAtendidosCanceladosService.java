package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.RelatorioPedidosAtendidosCanceladosResponse;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.strategy.CriterioPedidoAtendidoCancelado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioPedidosAtendidosCanceladosService {

    private final EventoPedidoRepository eventoPedidoRepository;
    private final CriterioPedidoAtendidoCancelado criterioPedidoAtendidoCancelado;

    @Transactional(readOnly = true)
    public RelatorioPedidosAtendidosCanceladosResponse gerar(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("dataInicio deve ser anterior ou igual a dataFim");
        }

        List<StatusPedido> statusAtendidos = criterioPedidoAtendidoCancelado.obterStatusAtendidos();
        if (statusAtendidos == null || statusAtendidos.isEmpty()) {
            statusAtendidos = List.of(StatusPedido.CONCLUIDO);
        }

        List<StatusPedido> statusCancelados = criterioPedidoAtendidoCancelado.obterStatusCancelados();
        if (statusCancelados == null || statusCancelados.isEmpty()) {
            statusCancelados = List.of(StatusPedido.CANCELADO);
        }

        long quantidadeAtendidos = eventoPedidoRepository.countByDataPedidoBetweenAndStatusIn(
                dataInicio,
                dataFim,
                statusAtendidos);

        long quantidadeCancelados = eventoPedidoRepository.countByDataPedidoBetweenAndStatusIn(
                dataInicio,
                dataFim,
                statusCancelados);

        return RelatorioPedidosAtendidosCanceladosResponse.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .quantidadeAtendidos(quantidadeAtendidos)
                .quantidadeCancelados(quantidadeCancelados)
                .saldo(quantidadeAtendidos - quantidadeCancelados)
                .build();
    }
}
