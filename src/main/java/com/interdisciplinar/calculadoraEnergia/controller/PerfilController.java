package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.service.PerfilService;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;
    private final UsuarioService usuarioService;  // Para buscar o usuário logado

    public PerfilController(PerfilService perfilService, UsuarioService usuarioService) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Perfil> criarPerfil(@RequestParam Perfil nomePerfil, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extrai o e-mail do usuário autenticado
        String email = (String) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarOuCriarUsuarioPorEmail(email);

        // Cria um novo perfil para o usuário autenticado
        Perfil perfilCriado = perfilService.criarPerfilParaUsuario(usuario, nomePerfil);

        return ResponseEntity.status(HttpStatus.CREATED).body(perfilCriado);
    }


    // Endpoint para atualizar um perfil utilizando o usuário autenticado
    @PutMapping("/{perfilId}")
    public CompletableFuture<ResponseEntity<Perfil>> atualizarPerfil(Authentication authentication, @PathVariable Long perfilId, @RequestBody Perfil perfilDTO) {
        // Extrair o email do principal autenticado
        String email = (String) authentication.getPrincipal();

        // Usar CompletableFuture para buscar o usuário de forma assíncrona
        return usuarioService.buscarUsuarioPorEmailAsync(email)
                .thenApply(usuario -> {
                    // Atualizar o perfil
                    Perfil perfilAtualizado = perfilService.atualizarPerfil(perfilId, perfilDTO);
                    return ResponseEntity.ok(perfilAtualizado); // Retorna o perfil atualizado no ResponseEntity
                });
    }

    // Endpoint para excluir um perfil utilizando o usuário autenticado
    @DeleteMapping("/{perfilId}")
    public CompletableFuture<ResponseEntity<Void>> excluirPerfil(Authentication authentication, @PathVariable Long perfilId) {
        // Extrair o email do principal autenticado
        String email = (String) authentication.getPrincipal();

        // Usar CompletableFuture para buscar o usuário de forma assíncrona
        return usuarioService.buscarUsuarioPorEmailAsync(email)
                .thenAccept(usuario -> {
                    // Excluir o perfil
                    perfilService.excluirPerfil(perfilId);
                }).thenApply(v -> ResponseEntity.noContent().build()); // Retorna 204 No Content
    }
}
