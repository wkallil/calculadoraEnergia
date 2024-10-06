    package com.interdisciplinar.calculadoraEnergia.controller.UsuarioController;

    import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
    import com.interdisciplinar.calculadoraEnergia.service.UsuarioService.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestHeader;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/users")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping("/authenticate")
        public ResponseEntity<Usuario> authenticate(@RequestHeader(value = "Authorization", required = false) String idToken) {
            if (idToken == null || !idToken.startsWith("Bearer ")) {
                return ResponseEntity.status(400).body(null); // Retorne um 400 se o token estiver ausente ou incorreto
            }

            try {
                // Remover "Bearer " se estiver presente
                idToken = idToken.substring(7);
                Usuario usuario = usuarioService.getOrCreateUser(idToken);
                return ResponseEntity.ok(usuario);
            } catch (Exception e) {
                return ResponseEntity.status(401).build();
            }
        }
    }
