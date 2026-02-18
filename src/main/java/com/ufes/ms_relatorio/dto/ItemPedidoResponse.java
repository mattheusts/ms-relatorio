package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoResponse {

  private Long id;
  private Long idProduto;
  private String nomeProduto;
  private Integer quantidade;
  private BigDecimal valorUnidade;
  private String observacao;
}
