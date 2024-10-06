package com.interdisciplinar.calculadoraEnergia.model.Usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Usuario {

    @Id
    private String id;

    private String email;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
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
