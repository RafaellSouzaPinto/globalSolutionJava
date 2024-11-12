package org.example.entities;

import java.time.LocalDateTime;

public class Viagem {

    private int id;
    private Usuario usuario;
    private MeioTransporte meioTransporte;
    private String enderecoOrigem;
    private String enderecoDestino;
    private double distancia;
    private int pontosGanhos;

    public Viagem() {
    }

    public Viagem(int id, Usuario usuario, MeioTransporte meioTransporte, String enderecoOrigem, String enderecoDestino, double distancia, int pontosGanhos) {
        this.id = id;
        this.usuario = usuario;
        this.meioTransporte = meioTransporte;
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.distancia = calcularDistancia();
        this.pontosGanhos = calcularPontos();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MeioTransporte getMeioTransporte() {
        return meioTransporte;
    }

    public void setMeioTransporte(MeioTransporte meioTransporte) {
        this.meioTransporte = meioTransporte;
    }

    public String getEnderecoOrigem() {
        return enderecoOrigem;
    }

    public void setEnderecoOrigem(String enderecoOrigem) {
        this.enderecoOrigem = enderecoOrigem;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }
    public double calcularDistancia() {
        Coordenadas origem = GeocodingUtils.geocodeEndereco(enderecoOrigem);
        Coordenadas destino = GeocodingUtils.geocodeEndereco(enderecoDestino);

        if (origem != null && destino != null) {
            return GeocodingUtils.calcularDistancia(origem, destino);
        } else {
            System.out.println("Não foi possível obter as coordenadas dos endereços.");
            return 0;
        }
    }

    public int calcularPontos() {
        this.pontosGanhos = (int) Math.floor(this.distancia);
        return this.pontosGanhos;
    }
}
