package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.service.PerfilService;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/me")
    public CompletableFuture<ResponseEntity<Set<Perfil>>> getPerfisByUsuario(Authentication authentication) {
        // Extrair o email do principal autenticado
        String email = (String) authentication.getPrincipal();

        // Usar CompletableFuture para buscar o usuário de forma assíncrona
        return usuarioService.buscarUsuarioPorEmailAsync(email)
                .thenApply(usuario -> {
                    // Depois que o usuário for retornado, buscar os perfis pelo ID do usuário
                    Long usuarioId = usuario.getId();
                    Set<Perfil> perfis = perfilService.buscarPerfisPorUsuarioId(usuarioId);
                    return ResponseEntity.ok(perfis);  // Retorna os perfis no ResponseEntity
                });
    }

    @PostMapping("/me")
    public CompletableFuture<ResponseEntity<Perfil>> criarPerfil(Authentication authentication, @RequestBody Perfil perfilDTO) {
        // Extrair o email do principal autenticado
        String email = (String) authentication.getPrincipal();

        // Usar CompletableFuture para buscar o usuário de forma assíncrona
        return usuarioService.buscarUsuarioPorEmailAsync(email)
                .thenApply(usuario -> {
                    // Criar o perfil para o usuário autenticado
                    Long usuarioId = usuario.getId();
                    Perfil perfilCriado = perfilService.criarPerfil(usuarioId, perfilDTO);
                    return ResponseEntity.ok(perfilCriado); // Retorna o perfil criado no ResponseEntity
                });
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
