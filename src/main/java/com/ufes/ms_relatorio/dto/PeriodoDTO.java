package com.ufes.ms_relatorio.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
