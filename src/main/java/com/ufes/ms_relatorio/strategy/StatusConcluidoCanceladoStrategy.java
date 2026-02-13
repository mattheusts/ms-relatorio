package com.ufes.ms_relatorio.strategy;

import com.ufes.ms_relatorio.entity.StatusPedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusConcluidoCanceladoStrategy implements CriterioPedidoAtendidoCancelado {

    private static final List<StatusPedido> STATUS_ATENDIDOS = List.of(StatusPedido.CONCLUIDO);
    private static final List<StatusPedido> STATUS_CANCELADOS = List.of(StatusPedido.CANCELADO);

    @Override
    public List<StatusPedido> obterStatusAtendidos() {
        return STATUS_ATENDIDOS;
    }

    @Override
    public List<StatusPedido> obterStatusCancelados() {
        return STATUS_CANCELADOS;
    }
}
