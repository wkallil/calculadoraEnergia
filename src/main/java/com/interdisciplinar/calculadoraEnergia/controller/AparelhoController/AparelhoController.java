package com.interdisciplinar.calculadoraEnergia.controller.AparelhoController;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.service.AparelhoService.AparelhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aparelhos")
public class AparelhoController {
    @Autowired
    private AparelhoService aparelhoService ;

    @PostMapping
    public ResponseEntity<Aparelho> criarDevice(@RequestBody Aparelho aparelho, @RequestParam Long profileId) {
        try {
            Aparelho novoDevice = aparelhoService.criarAparelho(aparelho, profileId);
            return ResponseEntity.ok(novoDevice);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Aparelho>> getDevicesByProfile(@PathVariable Long profileId) {
        List<Aparelho> devices = aparelhoService.getDevicesByProfile(profileId);
        return ResponseEntity.ok(devices);
    }

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
