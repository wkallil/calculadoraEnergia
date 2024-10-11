package com.interdisciplinar.calculadoraEnergia.model.Usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

/**
 * Entidade que representa um usuário no sistema.
 * Um usuário pode ter vários {@link Perfil} associados a ele.
 *
 * @author Whesley Kallil
 */
@Entity
public class Usuario {

    /**
     * Identificador único do usuário (UID).
     */
    @Id
    private String id;

    /**
     * Endereço de email do usuário.
     */
    private String email;

    /**
     * Conjunto de perfis associados ao usuário.
     */
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
