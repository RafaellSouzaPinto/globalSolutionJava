package org.example.entities;

import java.time.LocalDateTime;

public class Viagem {

    private int id;
    private Usuario usuario;
    private MeioTransporte meioTransporte;
    private String origem;
    private String destino;
    private float distancia;
    private LocalDateTime dataHora;
    private float velocidade;
    private int pontosGanhos;

    public Viagem() {
    }

    public Viagem(int pontosGanhos, float velocidade, LocalDateTime dataHora, float distancia, String destino, String origem, MeioTransporte meioTransporte, Usuario usuario, int id) {
        this.pontosGanhos = pontosGanhos;
        this.velocidade = velocidade;
        this.dataHora = dataHora;
        this.distancia = distancia;
        this.destino = destino;
        this.origem = origem;
        this.meioTransporte = meioTransporte;
        this.usuario = usuario;
        this.id = id;
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

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    private float calcularVelocidade() {
        // Implementar cálculo de velocidade com base na distância e tempo
        return this.meioTransporte.getVelocidadeMedia();
    }
    private int calcularPontos() {
        int pontosBase = (int) this.distancia; // Cada 1 km = 1 ponto
        if (this.usuario.getPlano().getNome().equalsIgnoreCase("Super Verdí")) {
            pontosBase *= 1.5;
        }
        return pontosBase;
    }
}
