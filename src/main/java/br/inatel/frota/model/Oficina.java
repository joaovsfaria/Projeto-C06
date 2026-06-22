package br.inatel.frota.model;

public class Oficina {

    private int idOficina;
    private String cnpj;
    private String rua;
    private String numero;
    private String bairro;
    private String cep;

    public Oficina() {
    }

    public Oficina(String cnpj, String rua, String numero, String bairro, String cep) {
        this.cnpj = cnpj;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
    }

    public Oficina(int idOficina, String cnpj, String rua, String numero, String bairro, String cep) {
        this.idOficina = idOficina;
        this.cnpj = cnpj;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
    }

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return String.format("Oficina [id=%d, cnpj=%s, %s, %s - %s, CEP %s]",
                idOficina, cnpj, rua, numero, bairro, cep);
    }
}
