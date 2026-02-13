package com.ufes.ms_relatorio.adapter;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosProduto {
    private Long idProduto;
    private String nomeProduto;
    private String categoria;
    private Boolean ativo;
}
