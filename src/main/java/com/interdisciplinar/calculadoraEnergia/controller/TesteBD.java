package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
public class TesteBD {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (DataAccessException e) {
            var message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
