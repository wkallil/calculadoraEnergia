package com.interdisciplinar.calculadoraEnergia.Configs;

import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoDeValor {
    private Double valorEnergia = 0.60; // Valor da energia por kWh (exemplo)
    private Double bandeira = 1.1; // Fatorr de bandeira (exemplo)

    public Double getValorEnergia() {
        return valorEnergia;
    }

    public void setValorEnergia(Double valorEnergia) {
        this.valorEnergia = valorEnergia;
    }

    public Double getBandeira() {
        return bandeira;
    }

    public void setBandeira(Double bandeira) {
        this.bandeira = bandeira;
    }
}
