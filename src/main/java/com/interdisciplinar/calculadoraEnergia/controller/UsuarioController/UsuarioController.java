    package com.interdisciplinar.calculadoraEnergia.controller.UsuarioController;

    import com.google.firebase.auth.FirebaseAuthException;
    import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
    import com.interdisciplinar.calculadoraEnergia.service.UsuarioService.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestHeader;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;


    /**
     * Controlador REST para gerenciar a autenticação de usuários via Firebase.
     * Através deste controlador, é possível autenticar usuários com um ID Token do Firebase e
     * buscar ou criar usuários no sistema.
     *
     * Endpoints:
     * - {@code POST /users/authenticate}: Autentica o usuário com base no ID Token fornecido no cabeçalho "Authorization".
     *
     * @author Whesley Kallil
     */
    @RestController
    @RequestMapping("/users")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        /**
         * Endpoint para autenticar um usuário com base no token de autenticação fornecido no cabeçalho "Authorization".
         * O token deve ser precedido por "Bearer ". Caso o token esteja ausente ou incorreto, retorna uma resposta HTTP 400.
         * Se o token for inválido, retorna uma resposta HTTP 401.
         *
         * @param idToken Token de autenticação Firebase enviado no cabeçalho "Authorization".
         * @return A entidade {@link Usuario} autenticada ou uma resposta de erro apropriada.
         */
        @PostMapping("/authenticate")
        public ResponseEntity<Usuario> authenticate(@RequestHeader(value = "Authorization", required = false) String idToken) {
            if (idToken == null || !idToken.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorne um 400 se o token estiver ausente ou incorreto
            }

            try {
                // Remover "Bearer " se estiver presente
                idToken = idToken.substring(7);
                Usuario usuario = usuarioService.getOrCreateUser(idToken); // Aqui, sua lógica de serviço
                return ResponseEntity.ok(usuario);
            } catch (FirebaseAuthException e) {
                // Token inválido, pode ser tratado aqui se necessário
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Retorne 401 se o token for inválido
            } catch (Exception e) {
                // Tratar outras exceções que podem ocorrer
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
