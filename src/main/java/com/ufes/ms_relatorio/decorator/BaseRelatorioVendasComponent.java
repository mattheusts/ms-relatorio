package com.ufes.ms_relatorio.decorator;

import com.ufes.ms_relatorio.dto.AgregadoVendasProdutoDTO;
import com.ufes.ms_relatorio.dto.PeriodoDTO;
import com.ufes.ms_relatorio.dto.RelatorioVendasRequest;
import com.ufes.ms_relatorio.dto.RelatorioVendasResult;
import com.ufes.ms_relatorio.repository.ItemPedidoRepository;
import com.ufes.ms_relatorio.strategy.CriterioVendaConcluida;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("baseRelatorio")
@RequiredArgsConstructor
public class BaseRelatorioVendasComponent implements RelatorioVendasComponent {

    private final ItemPedidoRepository itemPedidoRepository;
    private final CriterioVendaConcluida criterioVendaConcluida;

    @Override
    public RelatorioVendasResult gerar(RelatorioVendasRequest request) {
        LocalDateTime dataInicio = request.getDataInicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().plusDays(1).atStartOfDay();
        var statusConcluidos = criterioVendaConcluida.obterStatusConcluidos();

        List<AgregadoVendasProdutoDTO> agregados = itemPedidoRepository.agregarVendasPorProduto(
                dataInicio,
                dataFim,
                statusConcluidos,
                request.getFormaDePagamento()
        );

        PeriodoDTO periodo = PeriodoDTO.builder()
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .build();

        return RelatorioVendasResult.builder()
                .agregados(agregados)
                .periodo(periodo)
                .page(request.getPage())
                .size(request.getSize())
                .totalElements(agregados.size())
                .build();
    }
}
