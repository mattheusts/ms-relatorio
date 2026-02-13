package com.ufes.ms_relatorio.controller;

import com.ufes.ms_relatorio.dto.*;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.entity.TipoPeriodo;
import com.ufes.ms_relatorio.service.EventoPedidoService;
import com.ufes.ms_relatorio.service.RelatorioPedidosAtendidosCanceladosService;
import com.ufes.ms_relatorio.service.RelatorioTotalPorPeriodoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioTotalPorPeriodoService relatorioTotalPorPeriodoService;
    private final RelatorioPedidosAtendidosCanceladosService relatorioPedidosAtendidosCanceladosService;
    private final EventoPedidoService eventoPedidoService;

    @GetMapping("/total-por-periodo")
    public ResponseEntity<?> totalPorPeriodo(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "DIA") TipoPeriodo tipoPeriodo) {
        RelatorioTotalPorPeriodoRequest request = RelatorioTotalPorPeriodoRequest.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .tipoPeriodo(tipoPeriodo)
                .build();
        try {
            RelatorioTotalPorPeriodoResponse response = relatorioTotalPorPeriodoService.gerar(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("total-por-periodo: " + e.getClass().getSimpleName() + " - " + e.getMessage(),
                    e);
        }
    }

    @GetMapping("/pedidos")
    public ResponseEntity<Page<EventoPedidoResponse>> relatorioPedidos(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<EventoPedidoResponse> response = eventoPedidoService.listarPedidos(dataInicio, dataFim, status, page,
                size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendas")
    public ResponseEntity<RelatorioVendasTotalResponse> relatorioVendas(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        var totalVendas = eventoPedidoService.calcularTotalVendas(dataInicio, dataFim);
        RelatorioVendasTotalResponse response = RelatorioVendasTotalResponse.builder()
                .totalVendas(totalVendas)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pedidos-atendidos-cancelados")
    public ResponseEntity<RelatorioPedidosAtendidosCanceladosResponse> relatorioPedidosAtendidosCancelados(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        RelatorioPedidosAtendidosCanceladosResponse response = relatorioPedidosAtendidosCanceladosService.gerar(
                dataInicio,
                dataFim);
        return ResponseEntity.ok(response);
    }
}
