package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.model.Comodo;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Set<Perfil> buscarPerfisPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getPerfis();
    }

    @Transactional
    public Perfil criarPerfilParaUsuario(Usuario usuario, String nomePerfil) {
        // Cria um novo perfil associado ao usuário
        Perfil novoPerfil = new Perfil(nomePerfil, usuario);

        // Adiciona um cômodo padrão ao perfil (opcional)
        Comodo comodoPadrao = new Comodo("Cômodo Padrão", novoPerfil);
        novoPerfil.getComodos().add(comodoPadrao);

        // Persiste o perfil e o cômodo
        usuario.getPerfis().add(novoPerfil);
        usuarioRepository.save(usuario);

        return novoPerfil;
    }

    public Perfil atualizarPerfil(Long perfilId, Perfil perfilAtualizado) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfil.setNome(perfilAtualizado.getNome());
        perfilRepository.save(perfil);
        return perfil;
    }

    @Transactional
    public void excluirPerfil(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfilRepository.delete(perfil);
    }

}
