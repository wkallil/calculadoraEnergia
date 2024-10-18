package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.HistoricoMensal;
import com.interdisciplinar.calculadoraEnergia.service.HistoricoMensalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoMensalController {

    private final HistoricoMensalService historicoMensalService;

    public HistoricoMensalController(HistoricoMensalService historicoMensalService) {
        this.historicoMensalService = historicoMensalService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistoricoMensal>> getHistoricoMensalPorUsuario(@PathVariable Long usuarioId) {
        List<HistoricoMensal> historico = historicoMensalService.buscarHistoricoMensalPorUsuario(usuarioId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<HistoricoMensal>> getHistoricoMensalPorPerfil(@PathVariable Long perfilId) {
        List<HistoricoMensal> historico = historicoMensalService.buscarHistoricoMensalPorPerfil(perfilId);
        return ResponseEntity.ok(historico);
    }

    @PostMapping("/gerar")
    public ResponseEntity<Void> gerarHistoricoMensal() {
        historicoMensalService.gerarHistoricoMensalParaTodosPerfis();
        return ResponseEntity.ok().build();
    }
}
