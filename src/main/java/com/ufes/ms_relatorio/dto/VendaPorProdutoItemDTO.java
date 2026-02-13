package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaPorProdutoItemDTO {
    private Long idProduto;
    private String nomeProduto;
    private String categoria;
    private Long quantidadeTotalVendida;
    private BigDecimal valorTotalVendido;
    private PeriodoDTO periodo;
}
