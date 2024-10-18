package com.interdisciplinar.calculadoraEnergia.dto;

import java.util.Set;

public record PerfilDTO(String nome, Set<ComodoDTO> comodos) {
}
