package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioVendasResult {
    @Builder.Default
    private List<AgregadoVendasProdutoDTO> agregados = new ArrayList<>();
    @Builder.Default
    private List<VendaPorProdutoItemDTO> itens = new ArrayList<>();
    private PeriodoDTO periodo;
    private int page;
    private int size;
    private long totalElements;
}
