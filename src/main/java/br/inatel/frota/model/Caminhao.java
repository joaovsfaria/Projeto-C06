package br.inatel.frota.model;

public class Caminhao extends Veiculo {

    private int quantidadeEixos;

    public Caminhao() {
        super();
    }

    public Caminhao(String placa, double capacidadeCarga, int quantidadeEixos) {
        super(placa, capacidadeCarga);
        this.quantidadeEixos = quantidadeEixos;
    }

    public Caminhao(int idVeiculo, String placa, double capacidadeCarga, int quantidadeEixos) {
        super(idVeiculo, placa, capacidadeCarga);
        this.quantidadeEixos = quantidadeEixos;
    }

    public int getQuantidadeEixos() {
        return quantidadeEixos;
    }

    public void setQuantidadeEixos(int quantidadeEixos) {
        this.quantidadeEixos = quantidadeEixos;
    }

    @Override
    public String getTipo() {
        return "Caminhao";
    }

    @Override
    public double calcularCustoPorKm() {
        return 4.00 + (quantidadeEixos * 0.75);
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Quantidade de eixos: " + quantidadeEixos);
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d, placa=%s, capacidade=%.2f kg, eixos=%d, custoKm=R$ %.2f]",
                getTipo(), getIdVeiculo(), getPlaca(), getCapacidadeCarga(), quantidadeEixos, calcularCustoPorKm());
    }
}
