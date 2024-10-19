package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.historicoMensalDTO.HistoricoMensalDTO;
import com.interdisciplinar.calculadoraEnergia.model.HistoricoMensal;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.HistoricoMensalRepository;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoMensalService {


    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final HistoricoMensalRepository historicoMensalRepository;
    private final AparelhoService aparelhoService;

    public HistoricoMensalService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository,
                                  HistoricoMensalRepository historicoMensalRepository, AparelhoService aparelhoService) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.historicoMensalRepository = historicoMensalRepository;
        this.aparelhoService = aparelhoService;
    }

    @Transactional
    public void gerarHistoricoMensal(Usuario usuario, Perfil perfil, LocalDate mes) {
        double consumoTotal = perfil.getComodos().stream()
                .flatMap(comodo -> comodo.getAparelhos().stream())
                .mapToDouble(aparelhoService::calcularConsumoAparelho)
                .sum();

        double valorTotal = consumoTotal * 0.60 * 1.1;

        HistoricoMensal historico = new HistoricoMensal(usuario, perfil, consumoTotal, valorTotal, mes);
        historicoMensalRepository.save(historico);
    }

    public List<HistoricoMensalDTO> buscarHistoricoMensalPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<HistoricoMensal> historicoMensalList = historicoMensalRepository.findByUsuario(usuario);

        // Map HistoricoMensal to HistoricoMensalDTO
        return historicoMensalList.stream()
                .map(h -> new HistoricoMensalDTO(h.getConsumoTotal(), h.getValorTotal()))
                .collect(Collectors.toList());
    }

    public List<HistoricoMensalDTO> buscarHistoricoMensalPorPerfil(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        List<HistoricoMensal> historicoMensalList = historicoMensalRepository.findByPerfilAndMes(perfil, LocalDate.now().withDayOfMonth(1));

        // Map HistoricoMensal to HistoricoMensalDTO
        return historicoMensalList.stream()
                .map(h -> new HistoricoMensalDTO(h.getConsumoTotal(), h.getValorTotal()))
                .collect(Collectors.toList());
    }
}
