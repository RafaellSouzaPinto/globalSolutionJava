package org.example.entities;

import java.time.LocalDateTime;

public class Notificacao {
    private int id;
    private Usuario usuario;
    private String mensagem;
    private boolean lida;
    private LocalDateTime dataHora;

    public Notificacao() {
    }

    public Notificacao(int id, Usuario usuario, String mensagem, boolean lida, LocalDateTime dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.lida = false;
        this.dataHora = dataHora;
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
    // Método para marcar como lida
    public void marcarComoLida() {
        this.lida = true;
    }
}
