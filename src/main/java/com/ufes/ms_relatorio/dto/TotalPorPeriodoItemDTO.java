package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalPorPeriodoItemDTO {
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private String periodoLabel;
    private BigDecimal valorTotal;
    private Long quantidadePedidos;
}
