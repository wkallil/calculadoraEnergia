package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.dto.AparelhoDTO;
import com.interdisciplinar.calculadoraEnergia.dto.ComodoDTO;
import com.interdisciplinar.calculadoraEnergia.dto.PerfilDTO;
import com.interdisciplinar.calculadoraEnergia.dto.UsuarioDTO;
import com.interdisciplinar.calculadoraEnergia.model.Aparelho;
import com.interdisciplinar.calculadoraEnergia.model.Comodo;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario buscarOuCriarUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get(); // Retorna o usuário existente
        } else {
            // Criação do novo usuário com perfil e cômodo padrão
            Usuario novoUsuario = new Usuario(email);
            Perfil perfil = new Perfil("meu perfil", novoUsuario);
            Comodo comodo = new Comodo("meu cômodo", perfil);

            perfil.getComodos().add(comodo);
            novoUsuario.getPerfis().add(perfil);

            usuarioRepository.save(novoUsuario);

            return novoUsuario; // Retorna o novo usuário criado
        }
    }

    public Usuario criarUsuario(String email) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        return usuarioRepository.save(novoUsuario);

    }

    public UsuarioDTO mapToDTO(Usuario usuario) {
        // Implementação simples para mapear entidades para DTO
        return new UsuarioDTO(
                usuario.getEmail(),
                usuario.getPerfis().stream().map(perfil -> new PerfilDTO(
                        perfil.getNome(),
                        perfil.getComodos().stream().map(comodo -> new ComodoDTO(
                                comodo.getNome(),
                                comodo.getAparelhos().stream().map(aparelho -> new AparelhoDTO(
                                        aparelho.getNome(),
                                        aparelho.getPotencia(),
                                        aparelho.getHorasDeUso()
                                )).collect(Collectors.toSet())
                        )).collect(Collectors.toSet())
                )).collect(Collectors.toSet())
        );
    }

    public double calcularConsumo(Aparelho aparelho) {
        double consumoDiario = (aparelho.getPotencia() * aparelho.getHorasDeUso()) / 1000; // kWh por dia
        return consumoDiario * 30 * 0.60 * 1.1; // Consumo mensal com bandeira
    }

    public CompletableFuture<Usuario> buscarUsuarioPorEmailAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));
        });
    }

}
