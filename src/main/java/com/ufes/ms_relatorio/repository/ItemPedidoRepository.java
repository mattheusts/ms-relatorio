package com.ufes.ms_relatorio.repository;

import com.ufes.ms_relatorio.dto.AgregadoVendasProdutoDTO;
import com.ufes.ms_relatorio.entity.FormaDePagamento;
import com.ufes.ms_relatorio.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufes.ms_relatorio.entity.ItemPedido;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("""
        SELECT new com.ufes.ms_relatorio.dto.AgregadoVendasProdutoDTO(
            i.idProduto,
            SUM(i.quantidade),
            SUM(i.quantidade * i.valorUnidade)
        )
        FROM ItemPedido i
        JOIN i.pedido p
        WHERE p.dataPedido >= :dataInicio AND p.dataPedido < :dataFim
        AND p.status IN :statusConcluidos
        AND (:formaDePagamento IS NULL OR p.formaDePagamento = :formaDePagamento)
        GROUP BY i.idProduto
        """)
    List<AgregadoVendasProdutoDTO> agregarVendasPorProduto(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            @Param("statusConcluidos") List<StatusPedido> statusConcluidos,
            @Param("formaDePagamento") FormaDePagamento formaDePagamento
    );
}
