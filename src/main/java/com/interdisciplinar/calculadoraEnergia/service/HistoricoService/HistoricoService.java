package com.interdisciplinar.calculadoraEnergia.service.HistoricoService;

import com.interdisciplinar.calculadoraEnergia.model.Historico.Historico;
import com.interdisciplinar.calculadoraEnergia.repository.HistoricoRepository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas ao {@link Historico}.
 * Fornece métodos para salvar novos registros de histórico e recuperar históricos
 * associados a um usuário específico (identificado pelo UID).
 *
 * @author Whesley Kallil
 */
@Service
public class HistoricoService {
    @Autowired
    private HistoricoRepository historicoRepository;

    /**
     * Salva um novo registro de histórico no sistema.
     * Um histórico contém a previsão de preço com base no consumo de energia e o UID do usuário associado.
     *
     * @param uid UID do usuário para o qual o histórico será registrado.
     * @param previsaoDePreco Previsão de preço do consumo de energia.
     * @return O histórico salvo.
     */
    // Salvar novo histórico
    public Historico salvarHistorico(String uid, Double previsaoDePreco) {
        Historico historico = new Historico(uid, previsaoDePreco);
        return historicoRepository.save(historico);
    }

    /**
     * Recupera todos os registros de histórico associados a um determinado usuário.
     *
     * @param uid UID do usuário cujos históricos serão recuperados.
     * @return Lista de históricos relacionados ao UID.
     */
    // Buscar histórico pelo UID
    public List<Historico> getHistoricoByUid(String uid) {
        return historicoRepository.findByUid(uid);
    }
}
