package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.dto.AparelhoDTO;
import com.interdisciplinar.calculadoraEnergia.dto.ComodoDTO;
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

    public Set<ComodoDTO> buscarComodosPorPerfilId(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return perfil.getComodos().stream().map(this::mapToDTO).collect(Collectors.toSet());
    }

    public ComodoDTO criarComodo(Long perfilId, ComodoDTO comodoDTO) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        Comodo comodo = new Comodo(comodoDTO.nome(), perfil);
        perfil.getComodos().add(comodo);
        comodoRepository.save(comodo);
        return mapToDTO(comodo);
    }

    public ComodoDTO atualizarComodo(Long comodoId, ComodoDTO comodoDTO) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        comodo.setNome(comodoDTO.nome());
        comodoRepository.save(comodo);
        return mapToDTO(comodo);
    }

    @Transactional
    public void excluirComodo(Long comodoId) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        comodoRepository.delete(comodo);
    }

    private ComodoDTO mapToDTO(Comodo comodo) {
        return new ComodoDTO(
                comodo.getNome(),
                comodo.getAparelhos().stream().map(aparelho -> new AparelhoDTO(
                        aparelho.getNome(),
                        aparelho.getPotencia(),
                        aparelho.getHorasDeUso()
                )).collect(Collectors.toSet())
        );
    }
}
