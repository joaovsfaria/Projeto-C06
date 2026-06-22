package br.inatel.frota.model;

import java.time.LocalDate;

public class Manutencao {

    private int idManutencao;
    private Veiculo veiculo;
    private Oficina oficina;
    private LocalDate dataServico;
    private double custoTotal;

    public Manutencao() {
    }

    public Manutencao(Veiculo veiculo, Oficina oficina, LocalDate dataServico, double custoTotal) {
        this.veiculo = veiculo;
        this.oficina = oficina;
        this.dataServico = dataServico;
        this.custoTotal = custoTotal;
    }

    public Manutencao(int idManutencao, Veiculo veiculo, Oficina oficina, LocalDate dataServico, double custoTotal) {
        this.idManutencao = idManutencao;
        this.veiculo = veiculo;
        this.oficina = oficina;
        this.dataServico = dataServico;
        this.custoTotal = custoTotal;
    }

    public int getIdManutencao() {
        return idManutencao;
    }

    public void setIdManutencao(int idManutencao) {
        this.idManutencao = idManutencao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public LocalDate getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDate dataServico) {
        this.dataServico = dataServico;
    }

    public double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }

    @Override
    public String toString() {
        return String.format("Manutencao [id=%d, idVeiculo=%d, idOficina=%d, data=%s, custo=R$ %.2f]",
                idManutencao, 
                (veiculo != null ? veiculo.getIdVeiculo() : 0), 
                (oficina != null ? oficina.getIdOficina() : 0), 
                dataServico, custoTotal);
    }
}
