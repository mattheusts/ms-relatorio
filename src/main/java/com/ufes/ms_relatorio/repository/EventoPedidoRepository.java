package com.ufes.ms_relatorio.repository;

import com.ufes.ms_relatorio.entity.EventoPedido;
import com.ufes.ms_relatorio.entity.StatusPedido;
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

        @Query("SELECT COALESCE(SUM(e.valorPedido), 0) FROM EventoPedido e " +
                        "WHERE e.dataPedido BETWEEN :dataInicio AND :dataFim " +
                        "AND e.status = 'CONCLUIDO'")
        BigDecimal somarVendasConcluidas(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);
}
