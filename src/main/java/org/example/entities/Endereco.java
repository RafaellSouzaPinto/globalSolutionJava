package org.example.entities;

public class Endereco {

    private int id;
    private Pessoa pessoa;
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {
    }

    public Endereco(String cep, String estado, String cidade, String numero, String logradouro, Pessoa pessoa, int id) {
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.numero = numero;
        this.logradouro = logradouro;
        this.pessoa = pessoa;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEnderecoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append(logradouro).append(", ").append(numero).append("\n");
        sb.append(cidade).append(" - ").append(estado).append("\n");
        sb.append("CEP: ").append(cep);
        return sb.toString();
    }
    public boolean pertenceACidade(String cidade) {
        return this.cidade.equalsIgnoreCase(cidade);
    }
    public void atualizarEndereco(String logradouro, String numero, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Endereco outroEndereco = (Endereco) obj;
        return id == outroEndereco.id &&
                pessoa.equals(outroEndereco.pessoa) &&
                logradouro.equals(outroEndereco.logradouro) &&
                numero.equals(outroEndereco.numero) &&
                cidade.equals(outroEndereco.cidade) &&
                estado.equals(outroEndereco.estado) &&
                cep.equals(outroEndereco.cep);
    }
}
