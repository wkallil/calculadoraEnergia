package com.interdisciplinar.calculadoraEnergia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class HistoricoMensal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private Double consumoTotal; // Consumo total em KWh
    private Double valorTotal;   // Valor total em R$

    private LocalDate mes;       // Mês referente ao histórico

    public HistoricoMensal() {}

    public HistoricoMensal(Usuario usuario, Perfil perfil, Double consumoTotal, Double valorTotal, LocalDate mes) {
        this.usuario = usuario;
        this.perfil = perfil;
        this.consumoTotal = consumoTotal;
        this.valorTotal = valorTotal;
        this.mes = mes;
    }

}
