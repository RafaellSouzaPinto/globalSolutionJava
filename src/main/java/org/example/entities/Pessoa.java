package org.example.entities;


public class Pessoa {
    private String planos;
    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private String email;

    private int pontos;
    private int creditos;
    private double distanciaAcumulada;

    public Pessoa() {}

    public Pessoa(int id, String nome, String cpf, String email, String senha, String planos) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.planos = planos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public double getDistanciaAcumulada() {
        return distanciaAcumulada;
    }

    public void setDistanciaAcumulada(double distanciaAcumulada) {
        this.distanciaAcumulada = distanciaAcumulada;
    }

    public String getPlanos() {
        return planos;
    }

    public void setPlanos(String planos) {
        this.planos = planos;
    }
    public String exibirResumo() {
        return "Pessoa {" +
                "ID=" + id +
                ", Nome='" + nome + '\'' +
                ", Plano='" + planos + '\'' +
                ", Pontos=" + pontos +
                ", Créditos=" + creditos +
                ", Distância Acumulada=" + distanciaAcumulada + " km" +
                '}';
    }
    public void reiniciarPontosECreditos() {
        this.pontos = 0;
        this.creditos = 0;
    }


}
