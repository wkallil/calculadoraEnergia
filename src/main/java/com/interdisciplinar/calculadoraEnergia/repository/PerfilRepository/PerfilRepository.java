package com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository;

import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    List<Perfil> findByUsuarioId(String usuarioId); // Método customizado para buscar perfis pelo ID do usuário
}
