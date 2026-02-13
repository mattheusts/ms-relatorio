package com.ufes.ms_relatorio.repository;

import com.ufes.ms_relatorio.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    List<Cardapio> findByIdProdutoIn(List<Long> idProdutos);
}
