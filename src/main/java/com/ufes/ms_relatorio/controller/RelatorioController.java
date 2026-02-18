package com.ufes.ms_relatorio.controller;

import com.ufes.ms_relatorio.domain.Periodo;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.dto.RelatorioItemMaisVendidoResponse;
import com.ufes.ms_relatorio.dto.RelatorioPedidosAtendidosCanceladosResponse;
import com.ufes.ms_relatorio.dto.RelatorioTotalPorPeriodoRequest;
import com.ufes.ms_relatorio.dto.RelatorioTotalPorPeriodoResponse;
import com.ufes.ms_relatorio.dto.RelatorioVendasTotalResponse;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.service.RelatorioItensMaisVendidosService;
import com.ufes.ms_relatorio.service.RelatorioPedidosAtendidosCanceladosService;
import com.ufes.ms_relatorio.service.RelatorioPedidosService;
import com.ufes.ms_relatorio.service.RelatorioTotalPorPeriodoService;
import com.ufes.ms_relatorio.service.RelatorioVendasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios de pedidos")
public class RelatorioController {

        private final RelatorioTotalPorPeriodoService relatorioTotalPorPeriodoService;
        private final RelatorioPedidosAtendidosCanceladosService relatorioPedidosAtendidosCanceladosService;
        private final RelatorioPedidosService relatorioPedidosService;
        private final RelatorioVendasService relatorioVendasService;
        private final RelatorioItensMaisVendidosService relatorioItensMaisVendidosService;

        @GetMapping("/total-por-periodo")
        @Operation(summary = "Total por período", description = "Retorna o valor total e quantidade de pedidos concluídos em um período de datas")
        public ResponseEntity<RelatorioTotalPorPeriodoResponse> totalPorPeriodo(
                        @Parameter(description = "Data de início (formato: yyyy-MM-dd)", example = "2025-01-01") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                        @Parameter(description = "Data de fim (formato: yyyy-MM-dd)", example = "2025-12-31") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
                RelatorioTotalPorPeriodoRequest request = RelatorioTotalPorPeriodoRequest.builder()
                                .dataInicio(dataInicio)
                                .dataFim(dataFim)
                                .build();
                RelatorioTotalPorPeriodoResponse response = relatorioTotalPorPeriodoService.gerar(request);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/pedidos")
        @Operation(summary = "Listar pedidos", description = "Retorna uma listagem paginada de pedidos filtrados por período e, opcionalmente, por status")
        public ResponseEntity<Page<EventoPedidoResponse>> relatorioPedidos(
                        @Parameter(description = "Data/hora de início (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-01-01T00:00:00") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                        @Parameter(description = "Data/hora de fim (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-12-31T23:59:59") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
                        @Parameter(description = "Filtro opcional por status do pedido") @RequestParam(required = false) StatusPedido status,
                        @Parameter(description = "Número da página (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
                        @Parameter(description = "Tamanho da página", example = "20") @RequestParam(defaultValue = "20") int size) {
                Periodo periodo = new Periodo(dataInicio, dataFim);
                Page<EventoPedidoResponse> response = relatorioPedidosService.listarPedidos(periodo, status, page,
                                size);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/vendas")
        @Operation(summary = "Total de vendas", description = "Retorna o valor total de vendas concluídas em um período")
        public ResponseEntity<RelatorioVendasTotalResponse> relatorioVendas(
                        @Parameter(description = "Data/hora de início (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-01-01T00:00:00") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                        @Parameter(description = "Data/hora de fim (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-12-31T23:59:59") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
                Periodo periodo = new Periodo(dataInicio, dataFim);
                RelatorioVendasTotalResponse response = RelatorioVendasTotalResponse.builder()
                                .totalVendas(relatorioVendasService.calcularTotalVendas(periodo))
                                .build();
                return ResponseEntity.ok(response);
        }

        @GetMapping("/pedidos-atendidos-cancelados")
        @Operation(summary = "Pedidos atendidos vs cancelados", description = "Retorna a contagem de pedidos atendidos e cancelados, saldo e valor total dos atendidos em um período")
        public ResponseEntity<RelatorioPedidosAtendidosCanceladosResponse> relatorioPedidosAtendidosCancelados(
                        @Parameter(description = "Data/hora de início (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-01-01T00:00:00") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                        @Parameter(description = "Data/hora de fim (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-12-31T23:59:59") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
                Periodo periodo = new Periodo(dataInicio, dataFim);
                RelatorioPedidosAtendidosCanceladosResponse response = relatorioPedidosAtendidosCanceladosService
                                .gerar(periodo);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/itens-mais-vendidos")
        @Operation(summary = "Itens mais vendidos", description = "Retorna os itens mais vendidos em um período, limitando a quantidade pelo parâmetro informado")
        public ResponseEntity<List<RelatorioItemMaisVendidoResponse>> relatorioItensMaisVendidos(
                        @Parameter(description = "Data/hora de início (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-01-01T00:00:00") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                        @Parameter(description = "Data/hora de fim (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2025-12-31T23:59:59") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
                        @Parameter(description = "Quantidade máxima de itens retornados", example = "10") @RequestParam int quantidade) {
                Periodo periodo = new Periodo(dataInicio, dataFim);
                List<RelatorioItemMaisVendidoResponse> response = relatorioItensMaisVendidosService
                                .listarItensMaisVendidos(periodo, quantidade);
                return ResponseEntity.ok(response);
        }
}
