package com.ufes.ms_relatorio.decorator;

import com.ufes.ms_relatorio.adapter.DadosProduto;
import com.ufes.ms_relatorio.adapter.ProdutoAdapter;
import com.ufes.ms_relatorio.dto.AgregadoVendasProdutoDTO;
import com.ufes.ms_relatorio.dto.RelatorioVendasRequest;
import com.ufes.ms_relatorio.dto.RelatorioVendasResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("filtrosOpcionais")
public class FiltrosOpcionaisDecorator implements RelatorioVendasComponent {

    private final RelatorioVendasComponent wrapped;
    private final ProdutoAdapter produtoAdapter;

    public FiltrosOpcionaisDecorator(
            @Qualifier("baseRelatorio") RelatorioVendasComponent wrapped,
            ProdutoAdapter produtoAdapter) {
        this.wrapped = wrapped;
        this.produtoAdapter = produtoAdapter;
    }

    @Override
    public RelatorioVendasResult gerar(RelatorioVendasRequest request) {
        RelatorioVendasResult result = wrapped.gerar(request);
        List<AgregadoVendasProdutoDTO> agregados = result.getAgregados();
        if (agregados == null || agregados.isEmpty()) {
            return result;
        }
        if (request.getCategoria() == null && request.getApenasAtivos() == null) {
            return result;
        }
        Set<Long> ids = agregados.stream().map(AgregadoVendasProdutoDTO::getIdProduto).collect(Collectors.toSet());
        var dadosProdutos = produtoAdapter.obterPorIds(ids);
        List<AgregadoVendasProdutoDTO> filtrados = agregados.stream()
                .filter(a -> {
                    DadosProduto dados = dadosProdutos.get(a.getIdProduto());
                    if (dados == null) return false;
                    if (Boolean.TRUE.equals(request.getApenasAtivos()) && !Boolean.TRUE.equals(dados.getAtivo())) {
                        return false;
                    }
                    if (request.getCategoria() != null && !request.getCategoria().equalsIgnoreCase(dados.getCategoria())) {
                        return false;
                    }
                    return true;
                })
                .toList();
        result.setAgregados(filtrados);
        result.setTotalElements(filtrados.size());
        return result;
    }
}
