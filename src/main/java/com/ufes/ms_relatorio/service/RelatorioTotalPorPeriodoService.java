package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.dto.*;
import com.ufes.ms_relatorio.entity.Pedido;
import com.ufes.ms_relatorio.entity.TipoPeriodo;
import com.ufes.ms_relatorio.repository.PedidoRepository;
import com.ufes.ms_relatorio.strategy.CriterioVendaConcluida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioTotalPorPeriodoService {

    private final PedidoRepository pedidoRepository;
    private final CriterioVendaConcluida criterioVendaConcluida;

    public RelatorioTotalPorPeriodoResponse gerar(RelatorioTotalPorPeriodoRequest request) {
        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new IllegalArgumentException("dataInicio deve ser anterior ou igual a dataFim");
        }
        TipoPeriodo tipo = request.getTipoPeriodo() != null ? request.getTipoPeriodo() : TipoPeriodo.DIA;
        LocalDateTime dataInicio = request.getDataInicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().plusDays(1).atStartOfDay();
        List<com.ufes.ms_relatorio.entity.StatusPedido> statusList = criterioVendaConcluida.obterStatusConcluidos();
        if (statusList == null || statusList.isEmpty()) {
            statusList = List.of(com.ufes.ms_relatorio.entity.StatusPedido.PAGO, com.ufes.ms_relatorio.entity.StatusPedido.ENTREGUE);
        }

        List<Pedido> pedidos = pedidoRepository.findByDataPedidoBetweenAndStatusIn(dataInicio, dataFim, statusList);

        Map<LocalDate, Agregado> agregados = new LinkedHashMap<>();
        for (Pedido p : pedidos) {
            LocalDate chave = chavePeriodo(p.getDataPedido().toLocalDate(), tipo);
            agregados.merge(chave, new Agregado(p.getValorTotal(), 1L), (a, b) -> new Agregado(
                    a.valorTotal.add(b.valorTotal), a.quantidade + b.quantidade));
        }

        List<TotalPorPeriodoItemDTO> itens = agregados.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> {
                    LocalDate periodoInicio = e.getKey();
                    Agregado a = e.getValue();
                    LocalDate periodoFim = calcularPeriodoFim(periodoInicio, tipo);
                    String label = formatarLabel(periodoInicio, tipo);
                    return TotalPorPeriodoItemDTO.builder()
                            .periodoInicio(periodoInicio)
                            .periodoFim(periodoFim)
                            .periodoLabel(label)
                            .valorTotal(a.valorTotal)
                            .quantidadePedidos(a.quantidade)
                            .build();
                })
                .collect(Collectors.toList());

        PeriodoDTO periodoConsulta = PeriodoDTO.builder()
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .build();

        return RelatorioTotalPorPeriodoResponse.builder()
                .itens(itens)
                .periodoConsulta(periodoConsulta)
                .tipoPeriodo(tipo)
                .build();
    }

    private LocalDate chavePeriodo(LocalDate data, TipoPeriodo tipo) {
        return switch (tipo) {
            case DIA -> data;
            case SEMANA -> data.with(DayOfWeek.MONDAY);
            case MES -> data.withDayOfMonth(1);
        };
    }

    private LocalDate calcularPeriodoFim(LocalDate inicio, TipoPeriodo tipo) {
        if (inicio == null) return null;
        return switch (tipo) {
            case DIA -> inicio;
            case SEMANA -> inicio.plusDays(6);
            case MES -> inicio.withDayOfMonth(inicio.lengthOfMonth());
        };
    }

    private String formatarLabel(LocalDate inicio, TipoPeriodo tipo) {
        if (inicio == null) return null;
        return switch (tipo) {
            case DIA -> inicio.toString();
            case SEMANA -> "Semana " + inicio;
            case MES -> inicio.getMonthValue() + "/" + inicio.getYear();
        };
    }

    private static class Agregado {
        final BigDecimal valorTotal;
        final long quantidade;

        Agregado(BigDecimal valorTotal, long quantidade) {
            this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
            this.quantidade = quantidade;
        }
    }
}
