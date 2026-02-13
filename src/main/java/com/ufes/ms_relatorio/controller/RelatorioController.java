package com.ufes.ms_relatorio.controller;

import com.ufes.ms_relatorio.dto.*;
import com.ufes.ms_relatorio.entity.FormaDePagamento;
import com.ufes.ms_relatorio.entity.TipoPeriodo;
import com.ufes.ms_relatorio.service.RelatorioTotalPorPeriodoService;
import com.ufes.ms_relatorio.service.RelatorioVendasService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioVendasService relatorioVendasService;
    private final RelatorioTotalPorPeriodoService relatorioTotalPorPeriodoService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/vendas-por-produto")
    public ResponseEntity<RelatorioVendasPorProdutoResponse> vendasPorProduto(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) FormaDePagamento formaDePagamento,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean apenasAtivos,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        RelatorioVendasRequest request = RelatorioVendasRequest.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .formaDePagamento(formaDePagamento)
                .categoria(categoria)
                .apenasAtivos(apenasAtivos)
                .page(page)
                .size(size)
                .build();
        RelatorioVendasPorProdutoResponse response = relatorioVendasService.gerarVendasPorProduto(request);
        return ResponseEntity.ok(response);
    }

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
            throw new RuntimeException("total-por-periodo: " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
        }
    }
}
