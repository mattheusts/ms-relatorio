package com.ufes.ms_relatorio.strategy;

import com.ufes.ms_relatorio.entity.StatusPedido;

import java.util.List;

public interface CriterioPedidoAtendidoCancelado {

    List<StatusPedido> obterStatusAtendidos();

    List<StatusPedido> obterStatusCancelados();
}
