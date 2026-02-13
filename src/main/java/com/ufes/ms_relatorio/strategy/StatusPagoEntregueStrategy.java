package com.ufes.ms_relatorio.strategy;

import com.ufes.ms_relatorio.entity.StatusPedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusPagoEntregueStrategy implements CriterioVendaConcluida {

    private static final List<StatusPedido> STATUS_CONCLUIDOS = List.of(StatusPedido.PAGO, StatusPedido.ENTREGUE);

    @Override
    public boolean isConcluido(StatusPedido status) {
        return STATUS_CONCLUIDOS.contains(status);
    }

    @Override
    public List<StatusPedido> obterStatusConcluidos() {
        return STATUS_CONCLUIDOS;
    }
}
