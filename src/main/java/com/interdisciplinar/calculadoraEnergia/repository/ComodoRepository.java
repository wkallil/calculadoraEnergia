package com.interdisciplinar.calculadoraEnergia.repository;

import com.interdisciplinar.calculadoraEnergia.model.Comodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComodoRepository extends JpaRepository<Comodo, String> {
}
