package com.interdisciplinar.calculadoraEnergia.model.Aparelho;

import com.interdisciplinar.calculadoraEnergia.model.Perfil.Perfil;

public class Aparelho {

    private Long id;
    private String nome;
    private Double potencia;
    private Double horasPorDia;
    private Integer diasPorMes;
    private Integer quantidade;
    private Double gastoMensal;
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
