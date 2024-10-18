package com.interdisciplinar.calculadoraEnergia.dto;

import java.util.Set;

public record ComodoDTO(String nome, Set<AparelhoDTO> aparelhos) {
}
