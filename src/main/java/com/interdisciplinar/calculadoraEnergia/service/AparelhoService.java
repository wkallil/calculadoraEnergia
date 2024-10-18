package com.interdisciplinar.calculadoraEnergia.service;

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

    public Set<Aparelho> buscarAparelhosPorComodoId(Long comodoId) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        return comodo.getAparelhos();
    }

    public Aparelho criarAparelho(Long comodoId, Aparelho aparelho) {
        Comodo comodo = comodoRepository.findById(comodoId)
                .orElseThrow(() -> new RuntimeException("Cômodo não encontrado"));
        aparelho.setComodo(comodo);
        comodo.getAparelhos().add(aparelho);
        aparelhoRepository.save(aparelho);
        return aparelho;
    }

    public Aparelho atualizarAparelho(Long aparelhoId, Aparelho aparelhoAtualizado) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new RuntimeException("Aparelho não encontrado"));
        aparelho.setNome(aparelhoAtualizado.getNome());
        aparelho.setPotencia(aparelhoAtualizado.getPotencia());
        aparelho.setHorasDeUso(aparelhoAtualizado.getHorasDeUso());
        aparelhoRepository.save(aparelho);
        return aparelho;
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

}
