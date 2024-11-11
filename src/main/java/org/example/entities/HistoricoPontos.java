package org.example.entities;

import java.time.LocalDateTime;

public class HistoricoPontos {
    private int id;
    private Usuario usuario;
    private LocalDateTime dataHora;
    private int pontos;
    private String descricao;

    public HistoricoPontos() {
    }

    public HistoricoPontos(int id, String descricao, int pontos, LocalDateTime dataHora, Usuario usuario) {
        this.id = id;
        this.descricao = descricao;
        this.pontos = pontos;
        this.dataHora = dataHora;
        this.usuario = usuario;
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
