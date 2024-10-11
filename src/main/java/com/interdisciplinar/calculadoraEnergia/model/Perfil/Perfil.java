package com.interdisciplinar.calculadoraEnergia.model.Perfil;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidade que representa um perfil de usuário no sistema.
 * Um perfil agrupa vários aparelhos e está associado a um {@link Usuario}.
 *
 * @author Whesley Kallil
 */
@Entity
public class Perfil {
    /**
     * Identificador único do perfil.
     */
    @Id
    private Long id;
    /**
     * Nome do perfil.
     */
    private String nome;
    /**
     * Usuário ao qual o perfil está associado.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    /**
     * Conjunto de aparelhos associados a este perfil.
     */
    @OneToMany(mappedBy = "perfil")
    @JsonBackReference
    private Set<Aparelho> aparelhos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Aparelho> getAparelhos() {
        return aparelhos;
    }

    public void setDevices(Set<Aparelho> aparelhos) {
        this.aparelhos = aparelhos;
    }

}
