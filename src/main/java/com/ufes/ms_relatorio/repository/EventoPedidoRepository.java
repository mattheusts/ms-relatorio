package com.ufes.ms_relatorio.repository;

import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.entity.StatusPedido;
import com.ufes.ms_relatorio.dto.RelatorioItemMaisVendidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface EventoPedidoRepository extends JpaRepository<EventoPedido, Long> {

        List<EventoPedido> findByDataPedidoBetweenAndStatusIn(
                        LocalDateTime dataInicio,
                        LocalDateTime dataFim,
                        List<StatusPedido> status);

        Page<EventoPedido> findByDataPedidoBetween(
                        LocalDateTime dataInicio,
                        LocalDateTime dataFim,
                        Pageable pageable);

        Page<EventoPedido> findByDataPedidoBetweenAndStatus(
                        LocalDateTime dataInicio,
                        LocalDateTime dataFim,
                        StatusPedido status,
                        Pageable pageable);

        long countByDataPedidoBetweenAndStatusIn(
                        LocalDateTime dataInicio,
                        LocalDateTime dataFim,
                        List<StatusPedido> status);

        @Query("SELECT COALESCE(SUM(e.valorPedido), 0) FROM EventoPedido e " +
                        "WHERE e.dataPedido BETWEEN :dataInicio AND :dataFim " +
                        "AND e.status IN :status")
        BigDecimal somarValorPedidosPorStatus(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim,
                        @Param("status") List<StatusPedido> status);

        @Query("SELECT COALESCE(SUM(e.valorPedido), 0) FROM EventoPedido e " +
                        "WHERE e.dataPedido BETWEEN :dataInicio AND :dataFim " +
                        "AND e.status = 'CONCLUIDO'")
        BigDecimal somarVendasConcluidas(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);

        @Query("""
                        SELECT new com.ufes.ms_relatorio.dto.RelatorioItemMaisVendidoResponse(
                                i.idProduto,
                                i.nomeProduto,
                                COALESCE(SUM(i.quantidade), 0L)
                        )
                        FROM ItemPedido i
                        JOIN i.eventoPedido e
                        WHERE e.dataPedido BETWEEN :dataInicio AND :dataFim
                        AND e.status IN :status
                        GROUP BY i.idProduto, i.nomeProduto
                        ORDER BY COALESCE(SUM(i.quantidade), 0L) DESC, i.idProduto ASC
                        """)
        List<RelatorioItemMaisVendidoResponse> buscarItensMaisVendidos(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim,
                        @Param("status") List<StatusPedido> status,
                        Pageable pageable);
}
