package com.interdisciplinar.calculadoraEnergia.controller;


import com.interdisciplinar.calculadoraEnergia.dto.ComodoDTO;
import com.interdisciplinar.calculadoraEnergia.service.ComodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comodos")
public class ComodoController {

    private final ComodoService comodoService;

    public ComodoController(ComodoService comodoService) {
        this.comodoService = comodoService;
    }

    @GetMapping("/{perfilId}")
    public ResponseEntity<Set<ComodoDTO>> getComodosByPerfil(@PathVariable Long perfilId) {
        Set<ComodoDTO> comodos = comodoService.buscarComodosPorPerfilId(perfilId);
        return ResponseEntity.ok(comodos);
    }

    @PostMapping("/{perfilId}")
    public ResponseEntity<ComodoDTO> criarComodo(@PathVariable Long perfilId, @RequestBody ComodoDTO comodoDTO) {
        ComodoDTO comodoCriado = comodoService.criarComodo(perfilId, comodoDTO);
        return ResponseEntity.ok(comodoCriado);
    }

    @PutMapping("/{comodoId}")
    public ResponseEntity<ComodoDTO> atualizarComodo(@PathVariable Long comodoId, @RequestBody ComodoDTO comodoDTO) {
        ComodoDTO comodoAtualizado = comodoService.atualizarComodo(comodoId, comodoDTO);
        return ResponseEntity.ok(comodoAtualizado);
    }

    @DeleteMapping("/{comodoId}")
    public ResponseEntity<Void> excluirComodo(@PathVariable Long comodoId) {
        comodoService.excluirComodo(comodoId);
        return ResponseEntity.noContent().build();
    }
}
