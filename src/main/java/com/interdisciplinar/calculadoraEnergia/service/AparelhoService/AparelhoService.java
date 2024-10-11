package com.interdisciplinar.calculadoraEnergia.service.AparelhoService;

import com.interdisciplinar.calculadoraEnergia.Configs.ConfiguracaoDeValor;
import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.repository.AparelhoRepository.AparelhoRepository;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.service.HistoricoService.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a {@link Aparelho}.
 * Este serviço oferece funcionalidades como criação de aparelhos, cálculo de consumo e custo total de energia,
 * além de gerenciamento de histórico de consumo.
 *
 * @author Whesley Kallil
 */
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

    /**
     * Cria um novo {@link Aparelho} e o associa a um perfil existente.
     * Também calcula e define o gasto mensal do aparelho em kWh.
     *
     * @param aparelho Instância do aparelho a ser criada.
     * @param perfilId ID do perfil ao qual o aparelho será associado.
     * @return O aparelho criado e salvo no banco de dados.
     */
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

    /**
     * Retorna a lista de aparelhos associados a um determinado perfil.
     *
     * @param profileId ID do perfil.
     * @return Lista de aparelhos associados ao perfil.
     */
    public List<Aparelho> getDevicesByProfile(Long profileId) {
        return aparelhoRepository.findByPerfilId(profileId);
    }

    /**
     * Calcula o gasto mensal de um aparelho em kWh.
     * O cálculo considera a potência do aparelho, o número de horas usadas por dia e os dias usados por mês.
     *
     * @param aparelho Instância do aparelho.
     * @return O gasto mensal em kWh.
     */
    // Método para calcular o gasto mensal em kWh
    public Double calcularGastoMensal(Aparelho aparelho) {
        // Potência em Watts * Horas usadas * Dias por mês / 100
        return (aparelho.getPotencia() * aparelho.getHorasPorDia() * aparelho.getDiasPorMes()) / 100;
    }


    /**
     * Calcula o custo total de energia para um perfil, levando em consideração o consumo total de todos os aparelhos
     * associados ao perfil e o custo da energia com base nas configurações do sistema.
     * O histórico de consumo também é registrado para o usuário do perfil.
     *
     * @param perfilId ID do perfil cujo custo será calculado.
     * @return O custo total de energia em reais (R$).
     */
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
