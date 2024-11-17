package org.example.entities;

public class Trajeto {
    private int id;
    private Pessoa pessoa;
    private String meioDeTransporte;
    private double distanciaKm;
    private int pontos;
    private String origem;
    private String destino;

    public Trajeto() {}

    public Trajeto(int id, Pessoa pessoa, String meioDeTransporte, double distanciaKm, int pontos, String origem, String destino) {
        this.id = id;
        this.pessoa = pessoa;
        this.meioDeTransporte = meioDeTransporte;
        this.distanciaKm = distanciaKm;
        this.pontos = pontos;
        this.origem = origem;
        this.destino = destino;
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

    public String getMeioDeTransporte() {
        return meioDeTransporte;
    }

    public void setMeioDeTransporte(String meioDeTransporte) {
        this.meioDeTransporte = meioDeTransporte;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}