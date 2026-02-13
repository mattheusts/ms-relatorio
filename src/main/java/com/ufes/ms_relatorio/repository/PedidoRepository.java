package com.ufes.ms_relatorio.repository;

import com.ufes.ms_relatorio.entity.Pedido;
import com.ufes.ms_relatorio.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByDataPedidoBetweenAndStatusIn(
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            List<StatusPedido> status
    );
}
