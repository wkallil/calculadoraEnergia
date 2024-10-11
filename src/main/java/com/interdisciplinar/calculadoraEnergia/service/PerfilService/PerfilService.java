package com.interdisciplinar.calculadoraEnergia.service.PerfilService;

import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository.PerfilRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas ao {@link Perfil}.
 * Fornece funcionalidades como criação de perfis, listagem de perfis por usuário, e exclusão de perfis com validação de autorização.
 *
 * @author Whesley Kallil
 */
@Service
public class PerfilService  {
    @Autowired
    private PerfilRepository perfilRepository;


    /**
     * Cria um novo {@link Perfil} e o associa a um usuário existente.
     *
     * @param perfil Instância do perfil a ser criada.
     * @param usuarioId ID do usuário ao qual o perfil será associado.
     * @return O perfil criado e salvo no banco de dados.
     */
    public Perfil criarPerfil(Perfil perfil, String usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        perfil.setUsuario(usuario);  // Definindo o usuário corretamente

        return perfilRepository.save(perfil);
    }

    /**
     * Retorna a lista de perfis associados a um determinado usuário.
     *
     * @param usuarioId ID do usuário.
     * @return Lista de perfis associados ao usuário.
     */
    public List<Perfil> getProfilesByUser(String usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId);  // Busca todos os perfis por usuário
    }


    /**
     * Exclui um perfil do sistema após verificar se ele pertence ao usuário especificado.
     * Lança uma exceção se o perfil não for encontrado ou se o usuário não estiver autorizado a deletar o perfil.
     *
     * @param perfilId ID do perfil a ser excluído.
     * @param usuarioId ID do usuário que está tentando deletar o perfil.
     * @throws RuntimeException Se o perfil não for encontrado ou se o usuário não estiver autorizado.
     */
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
