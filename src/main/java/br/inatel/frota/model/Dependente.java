package br.inatel.frota.model;

public class Dependente extends Pessoa {

    private int idDependente;
    private Motorista motorista;

    public Dependente() {
        super();
    }

    public Dependente(Motorista motorista, String nomeDependente) {
        super(nomeDependente);
        this.motorista = motorista;
    }

    public Dependente(int idDependente, Motorista motorista, String nomeDependente) {
        super(nomeDependente);
        this.idDependente = idDependente;
        this.motorista = motorista;
    }

    public int getIdDependente() {
        return idDependente;
    }

    public void setIdDependente(int idDependente) {
        this.idDependente = idDependente;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("=== Detalhes do Dependente ===");
        System.out.println("ID: " + idDependente);
        System.out.println("Nome: " + nome);
        if (motorista != null) {
            System.out.println("Dependente do Motorista: " + motorista.getNome());
        }
    }

    @Override
    public String toString() {
        return String.format("Dependente [id=%d, idMotorista=%d, nome=%s]",
                idDependente, (motorista != null ? motorista.getIdMotorista() : 0), nome);
    }
}
