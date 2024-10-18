package com.interdisciplinar.calculadoraEnergia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Comodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonBackReference
    private Perfil perfil;

    @OneToMany(mappedBy = "comodo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Aparelho> aparelhos = new HashSet<>();

    public Comodo() {}

    public Comodo(String nome, Perfil perfil) {
        this.nome = nome;
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comodo comodo = (Comodo) o;
        return Objects.equals(id, comodo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}