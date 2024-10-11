package com.interdisciplinar.calculadoraEnergia.controller.statusController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST simples para verificar o status da aplicação.
 * Este controlador expõe um único endpoint para verificar se a aplicação está funcionando corretamente.
 *
 * Endpoints:
 * - {@code GET /status}: Retorna uma mensagem indicando que o sistema está autorizado e funcionando.
 *
 * @author Whesley Kallil
 */
@RestController
@RequestMapping("/status")
public class StatusController {


    /**
     * Endpoint que retorna o status da aplicação.
     *
     * @return Uma string indicando que o sistema está "Autorizado".
     */
    @GetMapping
    public String getStatus() {
        return "Autorizado";
    }

    @PostMapping
    public String postStatus() {
        return "Post request received";
    }
}
