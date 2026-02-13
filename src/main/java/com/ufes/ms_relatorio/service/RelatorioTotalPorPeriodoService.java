package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.*;
import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.repository.EventoPedidoRepository;
import com.ufes.ms_relatorio.strategy.CriterioVendaConcluida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RelatorioTotalPorPeriodoService {

    private final EventoPedidoRepository eventoPedidoRepository;
    private final CriterioVendaConcluida criterioVendaConcluida;

    public RelatorioTotalPorPeriodoResponse gerar(RelatorioTotalPorPeriodoRequest request) {
        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new IllegalArgumentException("dataInicio deve ser anterior ou igual a dataFim");
        }

        LocalDate periodoInicio = request.getDataInicio();
        LocalDate periodoFim = request.getDataFim();

        LocalDateTime dataInicio = request.getDataInicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().plusDays(1).atStartOfDay();

        List<com.ufes.ms_relatorio.entity.StatusPedido> statusList = criterioVendaConcluida.obterStatusConcluidos();
        if (statusList == null || statusList.isEmpty()) {
            statusList = List.of(com.ufes.ms_relatorio.entity.StatusPedido.CONCLUIDO);
        }

        List<EventoPedido> pedidos = eventoPedidoRepository.findByDataPedidoBetweenAndStatusIn(
                dataInicio,
                dataFim,
                statusList);

        BigDecimal valorTotal = pedidos.stream()
                .map(EventoPedido::getValorPedido)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        long quantidadePedidos = pedidos.size();

        return RelatorioTotalPorPeriodoResponse.builder()
                .periodoInicio(periodoInicio)
                .periodoFim(periodoFim)
                .valorTotal(valorTotal)
                .quantidadePedidos(quantidadePedidos)
                .build();
    }
}
