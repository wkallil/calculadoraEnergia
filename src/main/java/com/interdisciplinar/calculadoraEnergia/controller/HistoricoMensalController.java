package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.historicoMensalDTO.HistoricoMensalDTO;
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
    public ResponseEntity<List<HistoricoMensalDTO>> getHistoricoMensalPorUsuario(@PathVariable Long usuarioId) {
        List<HistoricoMensalDTO> historico = historicoMensalService.buscarHistoricoMensalPorUsuario(usuarioId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<HistoricoMensalDTO>> getHistoricoMensalPorPerfil(@PathVariable Long perfilId) {
        List<HistoricoMensalDTO> historico = historicoMensalService.buscarHistoricoMensalPorPerfil(perfilId);
        return ResponseEntity.ok(historico);
    }

}
