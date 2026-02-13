package com.ufes.ms_relatorio.repository;

import java.math.BigDecimal;
import java.sql.Date;

public interface AgregadoTotalPorPeriodoProjection {

    Date getPeriodo();

    BigDecimal getValorTotal();

    Long getQuantidadePedidos();
}
