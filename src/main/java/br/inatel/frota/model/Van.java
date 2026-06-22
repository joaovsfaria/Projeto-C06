package br.inatel.frota.model;

public class Van extends Veiculo {

    private int quantidadePassageiros;

    public Van() {
        super();
    }

    public Van(String placa, double capacidadeCarga, int quantidadePassageiros) {
        super(placa, capacidadeCarga);
        this.quantidadePassageiros = quantidadePassageiros;
    }

    public Van(int idVeiculo, String placa, double capacidadeCarga, int quantidadePassageiros) {
        super(idVeiculo, placa, capacidadeCarga);
        this.quantidadePassageiros = quantidadePassageiros;
    }

    public int getQuantidadePassageiros() {
        return quantidadePassageiros;
    }

    public void setQuantidadePassageiros(int quantidadePassageiros) {
        this.quantidadePassageiros = quantidadePassageiros;
    }

    @Override
    public String getTipo() {
        return "Van";
    }

    @Override
    public double calcularCustoPorKm() {
        return 2.00 + (quantidadePassageiros * 0.10);
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Quantidade de passageiros: " + quantidadePassageiros);
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d, placa=%s, capacidade=%.2f kg, passageiros=%d, custoKm=R$ %.2f]",
                getTipo(), getIdVeiculo(), getPlaca(), getCapacidadeCarga(), quantidadePassageiros, calcularCustoPorKm());
    }
}
