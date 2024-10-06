package com.interdisciplinar.calculadoraEnergia.controller.HistoricoController;

import com.google.firebase.auth.FirebaseToken;
import com.interdisciplinar.calculadoraEnergia.model.Historico.Historico;
import com.interdisciplinar.calculadoraEnergia.service.HistoricoService.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

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

    // Endpoint para obter histórico por UID
    @GetMapping("/usuario")
    public ResponseEntity<List<Historico>> getHistoricoByUsuario(Authentication authentication) {
        FirebaseToken token = (FirebaseToken) authentication.getPrincipal();
        String uid = token.getUid();

        List<Historico> historicos = historicoService.getHistoricoByUid(uid);
        return ResponseEntity.ok(historicos);
    }
}

