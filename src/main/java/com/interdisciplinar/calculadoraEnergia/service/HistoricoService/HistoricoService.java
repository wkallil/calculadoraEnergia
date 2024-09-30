package com.interdisciplinar.calculadoraEnergia.service.HistoricoService;

import com.interdisciplinar.calculadoraEnergia.model.Historico.Historico;
import com.interdisciplinar.calculadoraEnergia.repository.HistoricoRepository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {
    @Autowired
    private HistoricoRepository historicoRepository;

    // Salvar novo histórico
    public Historico salvarHistorico(String uid, Double previsaoDePreco) {
        Historico historico = new Historico(uid, previsaoDePreco);
        return historicoRepository.save(historico);
    }

    // Buscar histórico pelo UID
    public List<Historico> getHistoricoByUid(String uid) {
        return historicoRepository.findByUid(uid);
    }
}
