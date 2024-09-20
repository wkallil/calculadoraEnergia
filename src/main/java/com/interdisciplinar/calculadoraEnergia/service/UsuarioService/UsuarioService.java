package com.interdisciplinar.calculadoraEnergia.service.UsuarioService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
