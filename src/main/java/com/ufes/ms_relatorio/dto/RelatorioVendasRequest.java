package com.ufes.ms_relatorio.dto;

import com.ufes.ms_relatorio.entity.FormaDePagamento;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioVendasRequest {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private FormaDePagamento formaDePagamento;
    private String categoria;
    private Boolean apenasAtivos;
    private int page;
    private int size;
}
