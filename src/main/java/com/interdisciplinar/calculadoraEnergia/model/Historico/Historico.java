package com.interdisciplinar.calculadoraEnergia.model.Historico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * Entidade que registra o histórico de consumo de energia do usuário.
 * O histórico contém previsões de preço e um timestamp do momento da geração.
 *
 * @author Whesley Kallil
 */
@Entity
public class Historico {
    /**
     * Identificador único do registro de histórico.
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Identificador do usuário relacionado ao histórico.
     */
    private String uid;
    /**
     * Previsão de preço do consumo de energia.
     */
    private Double previsaoDePreco;
    /**
     * Timestamp do momento em que o registro foi criado.
     */
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
