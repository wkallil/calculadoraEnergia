package com.interdisciplinar.calculadoraEnergia.service.UsuarioService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Serviço responsável pelo gerenciamento de usuários autenticados via Firebase.
 * Ele fornece funcionalidades para buscar ou criar um usuário baseado no ID Token retornado pelo Firebase.
 *
 * @author Whesley Kallil
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca um usuário existente no banco de dados com base no UID extraído de um ID Token do Firebase.
     * Se o usuário não existir, um novo usuário será criado e salvo com as informações do token.
     *
     * @param idToken Token de identificação do Firebase, fornecido durante a autenticação.
     * @return O usuário correspondente ao UID do token ou um novo usuário criado.
     * @throws Exception Se houver falha na verificação do ID Token.
     */
    public Usuario getOrCreateUser(String idToken) throws Exception{

        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        Optional<Usuario> userOpt = usuarioRepository.findById(uid);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            Usuario usuario = new Usuario();
            usuario.setId(uid);
            usuario.setEmail(decodedToken.getEmail());
            // Remove or add `name` attribute if necessary
            return usuarioRepository.save(usuario);
        }
    }
}
