package com.interdisciplinar.calculadoraEnergia.model.Aparelho;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
/**
 * Entidade que representa um aparelho eletrônico no sistema.
 * Um aparelho possui informações sobre seu nome, potência, tempo de uso diário e mensal,
 * além de estar associado a um {@link Perfil}.
 *
 * @author Whesley Kallil
 */
@Entity
public class Aparelho {
    /**
     * Identificador único do aparelho.
     */
    @Id
    private Long id;
    /**
     * Nome do aparelho.
     */
    private String nome;
    /**
     * Potência do aparelho em kW.
     */
    private Double potencia;
    /**
     * Número de horas que o aparelho é utilizado por dia.
     */
    private Double horasPorDia;

    /**
     * Número de dias que o aparelho é utilizado por mês.
     */
    private Integer diasPorMes;

    /**
     * Quantidade de aparelhos desse tipo.
     */
    private Integer quantidade;
    /**
     * Gasto mensal estimado em kWh.
     */
    private Double gastoMensal;

    /**
     * Perfil ao qual o aparelho está associado.
     */

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonManagedReference
    private Perfil perfil;

    // Getters e Setters
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

    public Double getPotencia() {
        return potencia;
    }

    public void setPotencia(Double potencia) {
        this.potencia = potencia;
    }

    public Double getHorasPorDia() {
        return horasPorDia;
    }

    public void setHorasPorDia(Double horasPorDia) {
        this.horasPorDia = horasPorDia;
    }

    public Integer getDiasPorMes() {
        return diasPorMes;
    }

    public void setDiasPorMes(Integer diasPorMes) {
        this.diasPorMes = diasPorMes;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getGastoMensal() {
        return gastoMensal;
    }

    public void setGastoMensal(Double gastoMensal) {
        this.gastoMensal = gastoMensal;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
