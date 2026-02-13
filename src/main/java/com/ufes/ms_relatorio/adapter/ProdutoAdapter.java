package com.ufes.ms_relatorio.adapter;

import com.ufes.ms_relatorio.entity.Cardapio;
import com.ufes.ms_relatorio.repository.CardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProdutoAdapter {

    private final CardapioRepository cardapioRepository;

    public Optional<DadosProduto> obterPorId(Long idProduto) {
        return cardapioRepository.findById(idProduto)
                .map(this::paraDadosProduto);
    }

    public Map<Long, DadosProduto> obterPorIds(Set<Long> idProdutos) {
        if (idProdutos == null || idProdutos.isEmpty()) {
            return Map.of();
        }
        List<Cardapio> cardapios = cardapioRepository.findByIdProdutoIn(List.copyOf(idProdutos));
        return cardapios.stream()
                .collect(Collectors.toMap(Cardapio::getIdProduto, this::paraDadosProduto));
    }

    private DadosProduto paraDadosProduto(Cardapio c) {
        return DadosProduto.builder()
                .idProduto(c.getIdProduto())
                .nomeProduto(c.getNomeProduto())
                .categoria(c.getCategoria())
                .ativo(c.getAtivo())
                .build();
    }
}
