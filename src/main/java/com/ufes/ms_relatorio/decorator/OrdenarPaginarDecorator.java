package com.ufes.ms_relatorio.decorator;

import com.ufes.ms_relatorio.dto.RelatorioVendasRequest;
import com.ufes.ms_relatorio.dto.RelatorioVendasResult;
import com.ufes.ms_relatorio.dto.VendaPorProdutoItemDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class OrdenarPaginarDecorator implements RelatorioVendasComponent {

    private final RelatorioVendasComponent wrapped;

    public OrdenarPaginarDecorator(@Qualifier("enriquecerProduto") RelatorioVendasComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public RelatorioVendasResult gerar(RelatorioVendasRequest request) {
        RelatorioVendasResult result = wrapped.gerar(request);
        List<VendaPorProdutoItemDTO> itens = result.getItens();
        if (itens == null || itens.isEmpty()) {
            result.setTotalElements(0);
            return result;
        }
        List<VendaPorProdutoItemDTO> ordenados = itens.stream()
                .sorted(Comparator.comparing(VendaPorProdutoItemDTO::getValorTotalVendido,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
        int page = request.getPage() >= 0 ? request.getPage() : 0;
        int size = request.getSize() > 0 ? request.getSize() : 20;
        int from = Math.min(page * size, ordenados.size());
        int to = Math.min(from + size, ordenados.size());
        List<VendaPorProdutoItemDTO> pagina = ordenados.subList(from, to);
        result.setItens(pagina);
        result.setTotalElements(ordenados.size());
        result.setPage(page);
        result.setSize(size);
        return result;
    }
}
