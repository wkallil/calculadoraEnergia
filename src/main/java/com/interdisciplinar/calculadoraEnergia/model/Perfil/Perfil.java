package com.interdisciplinar.calculadoraEnergia.model.Perfil;

import com.interdisciplinar.calculadoraEnergia.model.Aparelho.Aparelho;
import com.interdisciplinar.calculadoraEnergia.model.Usuario.Usuario;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Perfil {

    @Id
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "perfil")
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
