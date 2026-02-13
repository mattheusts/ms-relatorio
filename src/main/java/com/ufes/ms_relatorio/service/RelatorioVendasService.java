package com.ufes.ms_relatorio.service;

import com.ufes.ms_relatorio.decorator.RelatorioVendasComponent;
import com.ufes.ms_relatorio.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelatorioVendasService {

    private final RelatorioVendasComponent relatorioVendasComponent;

    public RelatorioVendasPorProdutoResponse gerarVendasPorProduto(RelatorioVendasRequest request) {
        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new IllegalArgumentException("dataInicio deve ser anterior ou igual a dataFim");
        }
        RelatorioVendasResult result = relatorioVendasComponent.gerar(request);
        return RelatorioVendasPorProdutoResponse.builder()
                .itens(result.getItens())
                .periodo(result.getPeriodo())
                .page(result.getPage())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .build();
    }
}
