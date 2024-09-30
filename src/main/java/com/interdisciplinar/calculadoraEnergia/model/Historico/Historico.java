package com.interdisciplinar.calculadoraEnergia.model.Historico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Historico {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private Double previsaoDePreco;
    private LocalDateTime timestamp;

    // Construtores, getters e setters

    public Historico() {}

    public Historico(String uid, Double previsaoDePreco) {
        this.uid = uid;
        this.previsaoDePreco = previsaoDePreco;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getPrevisaoDePreco() {
        return previsaoDePreco;
    }

    public void setPrevisaoDePreco(Double previsaoDePreco) {
        this.previsaoDePreco = previsaoDePreco;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
