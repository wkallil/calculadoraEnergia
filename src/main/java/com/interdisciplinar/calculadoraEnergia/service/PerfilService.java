package com.interdisciplinar.calculadoraEnergia.service;

import com.interdisciplinar.calculadoraEnergia.dto.AparelhoDTO;
import com.interdisciplinar.calculadoraEnergia.dto.ComodoDTO;
import com.interdisciplinar.calculadoraEnergia.dto.PerfilDTO;
import com.interdisciplinar.calculadoraEnergia.model.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Set<PerfilDTO> buscarPerfisPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getPerfis().stream().map(this::mapToDTO).collect(Collectors.toSet());
    }

    public PerfilDTO criarPerfil(Long usuarioId, PerfilDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Perfil perfil = new Perfil(perfilDTO.nome(), usuario);
        usuario.getPerfis().add(perfil);
        perfilRepository.save(perfil);
        return mapToDTO(perfil);
    }

    public PerfilDTO atualizarPerfil(Long perfilId, PerfilDTO perfilDTO) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfil.setNome(perfilDTO.nome());
        perfilRepository.save(perfil);
        return mapToDTO(perfil);
    }

    @Transactional
    public void excluirPerfil(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfilRepository.delete(perfil);
    }

    private PerfilDTO mapToDTO(Perfil perfil) {
        return new PerfilDTO(
                perfil.getNome(),
                perfil.getComodos().stream().map(comodo -> new ComodoDTO(
                        comodo.getNome(),
                        comodo.getAparelhos().stream().map(aparelho -> new AparelhoDTO(
                                aparelho.getNome(),
                                aparelho.getPotencia(),
                                aparelho.getHorasDeUso()
                        )).collect(Collectors.toSet())
                )).collect(Collectors.toSet())
        );
    }
}
