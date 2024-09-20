package com.interdisciplinar.calculadoraEnergia.model.Usuario;

import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;

import java.util.Set;

public class Usuario {

    private String id;

    private String email;

    private Set<Perfil> perfis;


    // Getters e setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

}
