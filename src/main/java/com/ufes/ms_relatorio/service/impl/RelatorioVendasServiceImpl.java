package com.ufes.ms_relatorio.service.impl;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.service.RelatorioVendasService;
import com.ufes.ms_relatorio.strategy.CriterioVendaConcluida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioVendasServiceImpl implements RelatorioVendasService {

  private final EventoPedidoRepository eventoPedidoRepository;
  private final CriterioVendaConcluida criterioVendaConcluida;

  @Override
  @Transactional(readOnly = true)
  public BigDecimal calcularTotalVendas(Periodo periodo) {
    List<StatusPedido> statusList = criterioVendaConcluida.obterStatusConcluidos();
    if (statusList == null || statusList.isEmpty()) {
      statusList = List.of(StatusPedido.CONCLUIDO);
    }

    BigDecimal total = eventoPedidoRepository.somarValorPedidosPorStatus(
        periodo.dataInicio(), periodo.dataFim(), statusList);

    return total != null ? total : BigDecimal.ZERO;
  }
}
