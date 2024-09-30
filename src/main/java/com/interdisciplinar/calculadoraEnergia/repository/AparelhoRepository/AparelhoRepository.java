package com.interdisciplinar.calculadoraEnergia.repository.AparelhoRepository;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AparelhoRepository extends JpaRepository<Aparelho, Long> {
    List<Aparelho> findByPerfilId(Long perfilId);
}
