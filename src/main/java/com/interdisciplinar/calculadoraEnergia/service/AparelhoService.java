package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.dto.AparelhoDTO;
import com.interdisciplinar.calculadoraEnergia.model.Aparelho;
import com.interdisciplinar.calculadoraEnergia.model.Comodo;
import com.interdisciplinar.calculadoraEnergia.repository.AparelhoRepository;
import com.interdisciplinar.calculadoraEnergia.repository.ComodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AparelhoService {

    private final AparelhoRepository aparelhoRepository;
    private final ComodoRepository comodoRepository;

    public AparelhoService(AparelhoRepository aparelhoRepository, ComodoRepository comodoRepository) {
        this.aparelhoRepository = aparelhoRepository;
        this.comodoRepository = comodoRepository;
    }

    public Set<AparelhoDTO> buscarAparelhosPorComodoId(Long comodoId) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        return comodo.getAparelhos().stream().map(this::mapToDTO).collect(Collectors.toSet());
    }

    public AparelhoDTO criarAparelho(Long comodoId, AparelhoDTO aparelhoDTO) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        Aparelho aparelho = new Aparelho(aparelhoDTO.nome(), aparelhoDTO.potencia(), aparelhoDTO.horasDeUso(), comodo);
        comodo.getAparelhos().add(aparelho);
        aparelhoRepository.save(aparelho);
        return mapToDTO(aparelho);
    }

    public AparelhoDTO atualizarAparelho(Long aparelhoId, AparelhoDTO aparelhoDTO) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new RuntimeException("Aparelho não encontrado"));
        aparelho.setNome(aparelhoDTO.nome());
        aparelho.setPotencia(aparelhoDTO.potencia());
        aparelho.setHorasDeUso(aparelhoDTO.horasDeUso());
        aparelhoRepository.save(aparelho);
        return mapToDTO(aparelho);
    }

    @Transactional
    public void excluirAparelho(Long aparelhoId) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new RuntimeException("Aparelho não encontrado"));
        aparelhoRepository.delete(aparelho);
    }

    public double calcularConsumoAparelho(Long aparelhoId) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new RuntimeException("Aparelho não encontrado"));
        return (aparelho.getPotencia() * aparelho.getHorasDeUso() * 30 / 1000) * 0.60 * 1.1;
    }

    public double calcularConsumoAparelho(Aparelho aparelho) {
        double consumoDiario = (aparelho.getPotencia() * aparelho.getHorasDeUso()) / 1000;

        return consumoDiario * 30 * 0.60 * 1.1;
    }

    private AparelhoDTO mapToDTO(Aparelho aparelho) {
        return new AparelhoDTO(
                aparelho.getNome(),
                aparelho.getPotencia(),
                aparelho.getHorasDeUso()
        );
    }
}
