package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioVendasPorProdutoResponse {
    private List<VendaPorProdutoItemDTO> itens;
    private PeriodoDTO periodo;
    private int page;
    private int size;
    private long totalElements;
}
