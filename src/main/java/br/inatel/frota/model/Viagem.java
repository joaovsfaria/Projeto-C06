package br.inatel.frota.model;

import java.time.LocalDateTime;

public class Viagem {

    private int idViagem;
    private Veiculo veiculo;
    private Motorista motorista;
    private LocalDateTime dataHoraSaida;
    private String destino;

    public Viagem() {
    }

    public Viagem(Veiculo veiculo, Motorista motorista, LocalDateTime dataHoraSaida, String destino) {
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.dataHoraSaida = dataHoraSaida;
        this.destino = destino;
    }

    public Viagem(int idViagem, Veiculo veiculo, Motorista motorista, LocalDateTime dataHoraSaida, String destino) {
        this.idViagem = idViagem;
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.dataHoraSaida = dataHoraSaida;
        this.destino = destino;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return String.format("Viagem [id=%d, idVeiculo=%d, idMotorista=%d, saida=%s, destino=%s]",
                idViagem, 
                (veiculo != null ? veiculo.getIdVeiculo() : 0), 
                (motorista != null ? motorista.getIdMotorista() : 0), 
                dataHoraSaida, destino);
    }
}
