package com.interdisciplinar.calculadoraEnergia.repository;

import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {
}
