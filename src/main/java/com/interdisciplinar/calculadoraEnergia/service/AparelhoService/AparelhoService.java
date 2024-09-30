package com.interdisciplinar.calculadoraEnergia.service.AparelhoService;

import com.interdisciplinar.calculadoraEnergia.Configs.ConfiguracaoDeValor;
import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.repository.AparelhoRepository.AparelhoRepository;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.service.HistoricoService.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AparelhoService {

    @Autowired
    private AparelhoRepository aparelhoRepository;

    @Autowired
    private PerfilRepository perfilRepository;  // Injeção do PerfilRepository

    @Autowired
    private HistoricoService historicoService;  // Injeção do HistoricoService

    @Autowired
    private ConfiguracaoDeValor configuracaoDeValor; // Injeção da configuração

    public Aparelho criarAparelho(Aparelho aparelho, Long perfilId) {
        // Definir o perfil do aparelho
        aparelho.setPerfil(new com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil());
        aparelho.getPerfil().setId(perfilId);

        // Calcular e definir o gasto mensal em kWh
        Double gastoMensal = calcularGastoMensal(aparelho);
        aparelho.setGastoMensal(gastoMensal);

        // Salvar o aparelho
        return aparelhoRepository.save(aparelho);
    }

    public List<Aparelho> getDevicesByProfile(Long profileId) {
        return aparelhoRepository.findByPerfilId(profileId);
    }

    // Método para calcular o gasto mensal em kWh
    public Double calcularGastoMensal(Aparelho aparelho) {
        // Potência em Watts * Horas usadas * Dias por mês / 100
        return (aparelho.getPotencia() * aparelho.getHorasPorDia() * aparelho.getDiasPorMes()) / 100;
    }

    // Método para calcular o custo total com base no gasto em kWh e nas configurações
    public Double calcularCustoTotal(Long perfilId) {
        // Obter todos os aparelhos associados ao perfil
        List<Aparelho> aparelhos = getDevicesByProfile(perfilId);

        // Calcular o gasto total em kWh de todos os aparelhos
        Double gastoTotal = aparelhos.stream()
                .mapToDouble(Aparelho::getGastoMensal)
                .sum();

        // Configurações
        Double valorEnergia = configuracaoDeValor.getValorEnergia(); // R$ por kWh
        Double bandeira = configuracaoDeValor.getBandeira(); // Fator de bandeira

        // Calcular o custo total
        Double custoTotal = gastoTotal * valorEnergia * bandeira;

        // Recuperar UID do perfil
        String uid = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"))
                .getUsuario().getId();

        // Registrar o histórico
        historicoService.salvarHistorico(uid, custoTotal);

        return custoTotal;
    }
}
