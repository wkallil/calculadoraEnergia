package com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;

import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {
}
