package com.interdisciplinar.calculadoraEnergia.dto;

import java.util.Set;

public record UsuarioDTO(String email, Set<PerfilDTO> perfis) {
}
