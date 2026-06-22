package br.inatel.frota.model;

import java.time.LocalDate;

public class Motorista extends Pessoa {

    private int idMotorista;
    private String cpf;
    private LocalDate dataNascimento;
    private boolean statusAtivo;

    public Motorista() {
        super();
    }

    public Motorista(String nome, String cpf, LocalDate dataNascimento, boolean statusAtivo) {
        super(nome);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.statusAtivo = statusAtivo;
    }

    public Motorista(int idMotorista, String nome, String cpf, LocalDate dataNascimento, boolean statusAtivo) {
        super(nome);
        this.idMotorista = idMotorista;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.statusAtivo = statusAtivo;
    }

    public int getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(int idMotorista) {
        this.idMotorista = idMotorista;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("=== Detalhes do Motorista ===");
        System.out.println("ID: " + idMotorista);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Nascimento: " + dataNascimento);
        System.out.println("Status: " + (statusAtivo ? "Ativo" : "Inativo"));
    }

    @Override
    public String toString() {
        return String.format("Motorista [id=%d, nome=%s, cpf=%s, nasc=%s, ativo=%s]",
                idMotorista, nome, cpf, dataNascimento, statusAtivo ? "Sim" : "Nao");
    }
}
