package com.interdisciplinar.calculadoraEnergia.controller.HistoricoController;

import com.google.firebase.auth.FirebaseToken;
import com.interdisciplinar.calculadoraEnergia.model.Historico.Historico;
import com.interdisciplinar.calculadoraEnergia.service.HistoricoService.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar o histórico de previsões de custo de energia.
 * Este controlador permite registrar novos históricos e buscar históricos baseados no usuário autenticado.
 *
 * Endpoints:
 * - {@code POST /historico/registrar}: Registra um novo histórico de previsão de custo de energia.
 * - {@code GET /historico/usuario}: Recupera o histórico associado ao usuário autenticado.
 *
 * A autenticação é feita via Firebase e o UID do usuário é utilizado para associar os históricos.
 *
 * @author Whesley Kallil
 */
@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    /**
     * Endpoint para registrar um novo histórico de previsão de custo de energia.
     * O histórico é associado ao usuário autenticado via Firebase, identificando-o pelo UID.
     *
     * @param previsaoDePreco Previsão do custo de energia, fornecido como um parâmetro de solicitação.
     * @param authentication Objeto de autenticação contendo o token do Firebase.
     * @return O histórico recém-criado e associado ao usuário autenticado.
     */
    // Endpoint para registrar um novo histórico
    @PostMapping("/registrar")
    public ResponseEntity<Historico> registrarHistorico(
            @RequestParam Double previsaoDePreco,  // Aqui você pode ajustar para usar @RequestBody se preferir
            Authentication authentication) {

        FirebaseToken token = (FirebaseToken) authentication.getPrincipal();
        String uid = token.getUid();

        Historico historico = historicoService.salvarHistorico(uid, previsaoDePreco);
        return ResponseEntity.ok(historico);
    }

    /**
     * Endpoint para recuperar o histórico de previsões de custo de energia associado ao usuário autenticado.
     * O histórico é buscado com base no UID do usuário extraído do token Firebase.
     *
     * @param authentication Objeto de autenticação contendo o token do Firebase.
     * @return Uma lista de históricos associados ao UID do usuário autenticado.
     */
    // Endpoint para obter histórico por UID
    @GetMapping("/usuario")
    public ResponseEntity<List<Historico>> getHistoricoByUsuario(Authentication authentication) {
        FirebaseToken token = (FirebaseToken) authentication.getPrincipal();
        String uid = token.getUid();

        List<Historico> historicos = historicoService.getHistoricoByUid(uid);
        return ResponseEntity.ok(historicos);
    }
}

