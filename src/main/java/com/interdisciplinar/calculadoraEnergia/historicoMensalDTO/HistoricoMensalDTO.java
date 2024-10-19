package com.interdisciplinar.calculadoraEnergia.historicoMensalDTO;

import java.time.LocalDate;

public record HistoricoMensalDTO(double consumoTotal, double valorTotal, LocalDate mes) {
}
