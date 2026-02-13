package com.ufes.ms_relatorio.decorator;

import com.ufes.ms_relatorio.adapter.DadosProduto;
import com.ufes.ms_relatorio.adapter.ProdutoAdapter;
import com.ufes.ms_relatorio.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("enriquecerProduto")
public class EnriquecerProdutoDecorator implements RelatorioVendasComponent {

    private final RelatorioVendasComponent wrapped;
    private final ProdutoAdapter produtoAdapter;

    public EnriquecerProdutoDecorator(
            @Qualifier("filtrosOpcionais") RelatorioVendasComponent wrapped,
            ProdutoAdapter produtoAdapter) {
        this.wrapped = wrapped;
        this.produtoAdapter = produtoAdapter;
    }

    @Override
    public RelatorioVendasResult gerar(RelatorioVendasRequest request) {
        RelatorioVendasResult result = wrapped.gerar(request);
        List<AgregadoVendasProdutoDTO> agregados = result.getAgregados();
        if (agregados == null || agregados.isEmpty()) {
            result.setItens(List.of());
            return result;
        }
        Set<Long> ids = agregados.stream().map(AgregadoVendasProdutoDTO::getIdProduto).collect(Collectors.toSet());
        var dadosProdutos = produtoAdapter.obterPorIds(ids);
        List<VendaPorProdutoItemDTO> itens = agregados.stream()
                .map(a -> {
                    DadosProduto dados = dadosProdutos.get(a.getIdProduto());
                    String nomeProduto = dados != null ? dados.getNomeProduto() : null;
                    String categoria = dados != null ? dados.getCategoria() : null;
                    return VendaPorProdutoItemDTO.builder()
                            .idProduto(a.getIdProduto())
                            .nomeProduto(nomeProduto)
                            .categoria(categoria)
                            .quantidadeTotalVendida(a.getQuantidadeTotalVendida())
                            .valorTotalVendido(a.getValorTotalVendido())
                            .periodo(result.getPeriodo())
                            .build();
                })
                .toList();
        result.setItens(itens);
        return result;
    }
}
