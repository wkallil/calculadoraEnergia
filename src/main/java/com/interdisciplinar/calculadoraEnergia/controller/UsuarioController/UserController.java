package com.interdisciplinar.calculadoraEnergia.controller.UsuarioController;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user")
    public String getUserInfo(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return "Email do usuário: " + email;
    }

}
