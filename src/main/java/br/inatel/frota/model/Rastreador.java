package br.inatel.frota.model;

import java.time.LocalDate;

public class Rastreador {

    private int idRastreador;
    private Veiculo veiculo;
    private String numeroSerie;
    private LocalDate dataAtivacao;

    public Rastreador() {
    }

    public Rastreador(Veiculo veiculo, String numeroSerie, LocalDate dataAtivacao) {
        this.veiculo = veiculo;
        this.numeroSerie = numeroSerie;
        this.dataAtivacao = dataAtivacao;
    }

    public Rastreador(int idRastreador, Veiculo veiculo, String numeroSerie, LocalDate dataAtivacao) {
        this.idRastreador = idRastreador;
        this.veiculo = veiculo;
        this.numeroSerie = numeroSerie;
        this.dataAtivacao = dataAtivacao;
    }

    public int getIdRastreador() {
        return idRastreador;
    }

    public void setIdRastreador(int idRastreador) {
        this.idRastreador = idRastreador;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getDataAtivacao() {
        return dataAtivacao;
    }

    public void setDataAtivacao(LocalDate dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    @Override
    public String toString() {
        return String.format("Rastreador [id=%d, idVeiculo=%d, serie=%s, ativacao=%s]",
                idRastreador, (veiculo != null ? veiculo.getIdVeiculo() : 0), numeroSerie, dataAtivacao);
    }
}
