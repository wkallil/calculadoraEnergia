package com.interdisciplinar.calculadoraEnergia.controller;

import com.interdisciplinar.calculadoraEnergia.model.Usuario;
import com.interdisciplinar.calculadoraEnergia.repository.UsuarioRepository;
import com.interdisciplinar.calculadoraEnergia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TesteBD {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/teste-bd")
    public ResponseEntity<List<Usuario>> testeBD() {
        List<Usuario> result = usuarioRepository.findAll();
        return ResponseEntity.ok(result);
    }
}
