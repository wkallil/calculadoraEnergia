package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.dto.UsuarioDTO;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuario(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = (String) authentication.getPrincipal(); // Método para extrair email do token
        Usuario usuario = usuarioService.buscarOuCriarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/user")
    public String getUserInfo(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return "Email do usuário: " + email;
    }

    @PostMapping("/me")
    public ResponseEntity<Usuario> criarUsuario(@AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername(); // Método para extrair email do token
        Usuario usuario = usuarioService.criarUsuario(email);
        return ResponseEntity.ok(usuario);
    }

}
