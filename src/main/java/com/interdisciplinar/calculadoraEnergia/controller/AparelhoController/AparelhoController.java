package com.interdisciplinar.calculadoraEnergia.controller.AparelhoController;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.service.AparelhoService.AparelhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar os aparelhos de energia.
 * Este controlador permite criar novos aparelhos, buscar aparelhos por perfil,
 * e calcular o custo total baseado nos aparelhos associados a um perfil específico.
 *
 * Endpoints:
 * - {@code POST /aparelhos}: Cria um novo aparelho associado a um perfil.
 * - {@code GET /aparelhos/profile/{profileId}}: Recupera todos os aparelhos associados a um perfil.
 * - {@code GET /aparelhos/profile/{profileId}/calcular-custo}: Calcula o custo total dos aparelhos associados a um perfil.
 *
 * As operações são realizadas através do serviço de aparelhos.
 *
 * @author Whesley Kallil
 */
@RestController
@RequestMapping("/aparelhos")
public class AparelhoController {
    @Autowired
    private AparelhoService aparelhoService ;


    /**
     * Endpoint para criar um novo aparelho associado a um perfil.
     * O perfil é identificado pelo ID fornecido como parâmetro de solicitação.
     *
     * @param aparelho Objeto {@link Aparelho} contendo as informações do aparelho a ser criado.
     * @param profileId ID do perfil ao qual o aparelho será associado.
     * @return O aparelho recém-criado associado ao perfil, ou um erro 400 se ocorrer um problema.
     */
    @PostMapping
    public ResponseEntity<Aparelho> criarDevice(@RequestBody Aparelho aparelho, @RequestParam Long profileId) {
        try {
            Aparelho novoDevice = aparelhoService.criarAparelho(aparelho, profileId);
            return ResponseEntity.ok(novoDevice);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    /**
     * Endpoint para recuperar todos os aparelhos associados a um perfil específico.
     * O perfil é identificado pelo ID fornecido na URL.
     *
     * @param profileId ID do perfil para o qual os aparelhos serão recuperados.
     * @return Uma lista de aparelhos associados ao perfil, ou uma resposta 404 se o perfil não for encontrado.
     */
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Aparelho>> getDevicesByProfile(@PathVariable Long profileId) {
        List<Aparelho> devices = aparelhoService.getDevicesByProfile(profileId);
        return ResponseEntity.ok(devices);
    }

    /**
     * Endpoint para calcular o custo total dos aparelhos associados a um perfil.
     * O perfil é identificado pelo ID fornecido na URL.
     *
     * @param profileId ID do perfil para o qual o custo total será calculado.
     * @return O custo total dos aparelhos associados ao perfil, ou um erro 400 se ocorrer um problema.
     */
    @GetMapping("/profile/{profileId}/calcular-custo")
    public ResponseEntity<Double> calcularCustoTotal(@PathVariable Long profileId) {
        try {
            Double custoTotal = aparelhoService.calcularCustoTotal(profileId);
            return ResponseEntity.ok(custoTotal);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
