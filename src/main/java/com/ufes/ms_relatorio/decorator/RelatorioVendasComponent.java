package com.ufes.ms_relatorio.decorator;

import com.ufes.ms_relatorio.dto.RelatorioVendasRequest;
import com.ufes.ms_relatorio.dto.RelatorioVendasResult;

public interface RelatorioVendasComponent {

    RelatorioVendasResult gerar(RelatorioVendasRequest request);
}
