package com.interdisciplinar.calculadoraEnergia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Aparelho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double potencia; // em Watts
    private Double horasDeUso; // por dia

    @ManyToOne
    @JoinColumn(name = "comodo_id")
    @JsonBackReference
    private Comodo comodo;

    public Aparelho() {}

    public Aparelho(String nome, Double potencia, Double horasDeUso, Comodo comodo) {
        this.nome = nome;
        this.potencia = potencia;
        this.horasDeUso = horasDeUso;
        this.comodo = comodo;
    }

}
