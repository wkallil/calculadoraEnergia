package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import com.interdisciplinar.calculadoraEnergia.service.PerfilService;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;
    private final UsuarioService usuarioService;  // Para buscar o usuário logado
    private final UsuarioRepository usuarioRepository;

    public PerfilController(PerfilService perfilService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/criar")
    public ResponseEntity<Perfil> criarPerfil(@RequestParam String nomePerfil, Authentication authentication) {
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


    @PutMapping("/editar/{id}")
    public ResponseEntity<Perfil> editarPerfil(
            @PathVariable Long id,
            @RequestParam String novoNomePerfil,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extrai o e-mail do usuário autenticado
        String email = (String) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarOuCriarUsuarioPorEmail(email);

        // Encontra o perfil do usuário pelo ID
        Optional<Perfil> perfilOptional = usuario.getPerfis().stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst();

        if (perfilOptional.isPresent()) {
            Perfil perfilParaEditar = perfilOptional.get();

            // Atualiza o nome do perfil
            perfilParaEditar.setNome(novoNomePerfil);

            // Persiste a mudança no banco de dados
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(perfilParaEditar);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para excluir um perfil utilizando o usuário autenticado
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPerfil(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extrai o e-mail do usuário autenticado
        String email = (String) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarOuCriarUsuarioPorEmail(email);

        // Encontra o perfil do usuário pelo ID
        Optional<Perfil> perfilOptional = usuario.getPerfis().stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst();

        if (perfilOptional.isPresent()) {
            Perfil perfilParaRemover = perfilOptional.get();
            usuario.getPerfis().remove(perfilParaRemover);
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        }
    }
