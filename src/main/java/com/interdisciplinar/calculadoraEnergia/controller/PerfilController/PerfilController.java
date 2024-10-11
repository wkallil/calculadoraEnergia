package com.interdisciplinar.calculadoraEnergia.controller.PerfilController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.google.firebase.auth.FirebaseToken;
import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.PerfilRepository.PerfilRepository;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST responsável por gerenciar perfis de usuários.
 * Permite criar perfis associados a um usuário autenticado via Firebase.
 *
 * Endpoints:
 * - {@code POST /perfil/criar}: Cria um novo perfil associado ao usuário autenticado.
 *
 * O controlador utiliza a autenticação Firebase para vincular o perfil ao usuário correspondente.
 *
 * @author Whesley Kallil
 */
@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;


    /**
     * Endpoint para criar um novo perfil para o usuário autenticado.
     * O usuário é identificado com base no token de autenticação do Firebase. Se o usuário não existir,
     * ele é criado automaticamente e o perfil é associado a esse usuário.
     *
     * @param perfil Objeto {@link Perfil} contendo as informações do perfil a ser criado.
     * @param authentication Objeto de autenticação contendo o token do Firebase.
     * @return O perfil recém-criado e associado ao usuário autenticado.
     */
    @PostMapping("/criar")
    public Perfil criarPerfil(@RequestBody Perfil perfil, Authentication authentication){

        // Aqui você recupera o token decodificado
        FirebaseToken token = (FirebaseToken) authentication.getPrincipal();
        String uid = token.getUid();


        // Buscar o usuário pelo UID ou criar um novo, se não existir
        Usuario usuario = usuarioRepository.findById(uid)
                .orElseGet(() -> {
                    Usuario newUser = new Usuario();
                    newUser.setId(uid);
                    newUser.setEmail(token.getEmail());
                    return usuarioRepository.save(newUser);
        });


        // Associar o perfil ao usuário autenticado
        perfil.setUsuario(usuario);
        return  perfilRepository.save(perfil);
    }

}
