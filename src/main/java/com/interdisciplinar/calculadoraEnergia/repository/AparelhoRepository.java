package com.interdisciplinar.calculadoraEnergia.repository;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AparelhoRepository extends JpaRepository<Aparelho, Long> {
}
