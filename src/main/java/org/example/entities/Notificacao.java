package org.example.entities;

import java.time.LocalDateTime;

public class Notificacao {
    private int id;
    private Pessoa usuario;
    private String mensagem;
    private boolean lida;
    private LocalDateTime dataHora;

    public Notificacao() {
    }

    public Notificacao(int id, Pessoa usuario, String mensagem, boolean lida, LocalDateTime dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.lida = lida;
        this.dataHora = dataHora;
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
