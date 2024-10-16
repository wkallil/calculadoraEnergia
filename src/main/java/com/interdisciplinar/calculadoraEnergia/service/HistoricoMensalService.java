package com.interdisciplinar.calculadoraEnergia.service;

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

@Service
public class HistoricoMensalService {

    private final HistoricoMensalRepository historicoMensalRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final AparelhoService aparelhoService;  // Para cálculo de consumo

    public HistoricoMensalService(HistoricoMensalRepository historicoMensalRepository,
                                  UsuarioRepository usuarioRepository,
                                  PerfilRepository perfilRepository,
                                  AparelhoService aparelhoService) {
        this.historicoMensalRepository = historicoMensalRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.aparelhoService = aparelhoService;
    }

    @Transactional
    public void gerarHistoricoMensalParaTodosPerfis() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1); // Primeiro dia do mês atual

        for (Usuario usuario : usuarios) {
            for (Perfil perfil : usuario.getPerfis()) {
                gerarHistoricoMensal(usuario, perfil, mesAtual);
            }
        }
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

    public List<HistoricoMensal> buscarHistoricoMensalPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return historicoMensalRepository.findByUsuario(usuario);
    }

    public List<HistoricoMensal> buscarHistoricoMensalPorPerfil(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return historicoMensalRepository.findByPerfilAndMes(perfil, LocalDate.now().withDayOfMonth(1));
    }
}
