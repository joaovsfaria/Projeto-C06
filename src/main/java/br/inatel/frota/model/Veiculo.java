package br.inatel.frota.model;

public class Veiculo {

    private int idVeiculo;
    private String placa;
    private double capacidadeCarga;

    public Veiculo() {
    }

    // id é AUTO_INCREMENT
    public Veiculo(String placa, double capacidadeCarga) {
        this.placa = placa;
        this.capacidadeCarga = capacidadeCarga;
    }

    public Veiculo(int idVeiculo, String placa, double capacidadeCarga) {
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.capacidadeCarga = capacidadeCarga;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

    @Override
    public String toString() {
        return String.format("Veiculo [id=%d, placa=%s, capacidade=%.2f kg]",
                idVeiculo, placa, capacidadeCarga);
    }
}
