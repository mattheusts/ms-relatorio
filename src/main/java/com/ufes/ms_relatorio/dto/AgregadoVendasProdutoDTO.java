package com.ufes.ms_relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgregadoVendasProdutoDTO {
    private Long idProduto;
    private Long quantidadeTotalVendida;
    private BigDecimal valorTotalVendido;
}
