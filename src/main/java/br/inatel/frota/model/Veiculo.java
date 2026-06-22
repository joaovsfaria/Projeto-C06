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

    public String getTipo() {
        return "Generico";
    }

    public double calcularCustoPorKm() {
        return 2.50;
    }

    public void exibirDetalhes() {
        System.out.println("=== Detalhes do Veiculo ===");
        System.out.println("ID: " + idVeiculo);
        System.out.println("Tipo: " + getTipo());
        System.out.println("Placa: " + placa);
        System.out.println("Capacidade: " + capacidadeCarga + " kg");
        System.out.printf("Custo estimado por km: R$ %.2f%n", calcularCustoPorKm());
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d, placa=%s, capacidade=%.2f kg, custoKm=R$ %.2f]",
                getTipo(), idVeiculo, placa, capacidadeCarga, calcularCustoPorKm());
    }
}
