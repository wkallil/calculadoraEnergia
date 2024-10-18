package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho;
import com.interdisciplinar.calculadoraEnergia.service.AparelhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/aparelhos")
public class AparelhoController {

    private final AparelhoService aparelhoService;

    public AparelhoController(AparelhoService aparelhoService) {
        this.aparelhoService = aparelhoService;
    }

    @GetMapping("/{comodoId}")
    public ResponseEntity<Set<Aparelho>> getAparelhosByComodo(@PathVariable Long comodoId) {
        Set<Aparelho> aparelhos = aparelhoService.buscarAparelhosPorComodoId(comodoId);
        return ResponseEntity.ok(aparelhos);
    }

    @PostMapping("/{comodoId}")
    public ResponseEntity<Aparelho> criarAparelho(@PathVariable Long comodoId, @RequestBody Aparelho aparelhoDTO) {
        Aparelho aparelhoCriado = aparelhoService.criarAparelho(comodoId, aparelhoDTO);
        return ResponseEntity.ok(aparelhoCriado);
    }

    @PutMapping("/{aparelhoId}")
    public ResponseEntity<Aparelho> atualizarAparelho(@PathVariable Long aparelhoId, @RequestBody Aparelho aparelhoDTO) {
        Aparelho aparelhoAtualizado = aparelhoService.atualizarAparelho(aparelhoId, aparelhoDTO);
        return ResponseEntity.ok(aparelhoAtualizado);
    }

    @DeleteMapping("/{aparelhoId}")
    public ResponseEntity<Void> excluirAparelho(@PathVariable Long aparelhoId) {
        aparelhoService.excluirAparelho(aparelhoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consumo/{aparelhoId}")
    public ResponseEntity<Double> calcularConsumo(@PathVariable Long aparelhoId) {
        double consumo = aparelhoService.calcularConsumoAparelho(aparelhoId);
        return ResponseEntity.ok(consumo);
    }
}
