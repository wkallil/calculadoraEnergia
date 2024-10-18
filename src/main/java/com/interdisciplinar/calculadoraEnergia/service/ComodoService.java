package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.model.Comodo;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.repository.ComodoRepository;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComodoService {

    private final ComodoRepository comodoRepository;
    private final PerfilRepository perfilRepository;

    public ComodoService(ComodoRepository comodoRepository, PerfilRepository perfilRepository) {
        this.comodoRepository = comodoRepository;
        this.perfilRepository = perfilRepository;
    }

    public Set<Comodo> buscarComodosPorPerfilId(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return perfil.getComodos();
    }

    public Comodo criarComodo(Long perfilId, Comodo comodo) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        comodo.setPerfil(perfil);
        perfil.getComodos().add(comodo);
        comodoRepository.save(comodo);
        return comodo;
    }

    public Comodo atualizarComodo(Long comodoId, Comodo comodoAtualizado) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        comodo.setNome(comodoAtualizado.getNome());
        comodoRepository.save(comodo);
        return comodo;
    }

    @Transactional
    public void excluirComodo(Long comodoId) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        comodoRepository.delete(comodo);
    }

}
