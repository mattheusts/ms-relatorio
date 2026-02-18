package com.ufes.ms_relatorio.domain;

import java.time.LocalDateTime;

public record Periodo(LocalDateTime dataInicio, LocalDateTime dataFim) {

  public Periodo {
    if (dataInicio == null || dataFim == null) {
      throw new IllegalArgumentException("dataInicio e dataFim são obrigatórios");
    }
    if (dataInicio.isAfter(dataFim)) {
      throw new IllegalArgumentException("dataInicio deve ser anterior ou igual a dataFim");
    }
  }
}
