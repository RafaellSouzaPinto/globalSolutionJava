package org.example.entities;

import java.time.LocalDateTime;

public class HistoricoPontos {
    private int id;
    private Pessoa usuario;
    private LocalDateTime dataHora;
    private int pontos;
    private String descricao;

    public HistoricoPontos() {
    }

    public HistoricoPontos(int id, Pessoa usuario, LocalDateTime dataHora, int pontos, String descricao) {
        this.id = id;
        this.usuario = usuario;
        this.dataHora = dataHora;
        this.pontos = pontos;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
