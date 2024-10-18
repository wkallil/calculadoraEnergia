package com.interdisciplinar.calculadoraEnergia.repository;

import com.interdisciplinar.calculadoraEnergia.model.HistoricoMensal;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoricoMensalRepository extends JpaRepository<HistoricoMensal, Long> {
    List<HistoricoMensal> findByUsuarioAndMes(Usuario usuario, LocalDate mes);
    List<HistoricoMensal> findByPerfilAndMes(Perfil perfil, LocalDate mes);
    List<HistoricoMensal> findByUsuario(Usuario usuario);
}
