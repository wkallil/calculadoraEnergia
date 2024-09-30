package com.interdisciplinar.calculadoraEnergia.repository.HistoricoRepository;

import com.interdisciplinar.calculadoraEnergia.model.Historico.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByUid(String uid);  // Buscar históricos por UID
}
