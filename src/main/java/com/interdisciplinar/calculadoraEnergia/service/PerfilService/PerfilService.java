package com.interdisciplinar.calculadoraEnergia.service.PerfilService;

import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository.PerfilRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService  {
    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil criarPerfil(Perfil perfil, String usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        perfil.setUsuario(usuario);  // Definindo o usuário corretamente

        return perfilRepository.save(perfil);
    }

    public List<Perfil> getProfilesByUser(String usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId);  // Busca todos os perfis por usuário
    }



    @Transactional
    public void deletarPerfil(Long perfilId, String usuarioId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        // Verifica se o perfil pertence ao usuário
        if (!perfil.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este perfil");
        }

        perfilRepository.delete(perfil);
    }
}
