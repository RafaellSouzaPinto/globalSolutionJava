package org.example.entities;

import java.time.LocalDateTime;

public class Feedback {
    private int id;
    private Pessoa usuario;
    private String mensagem;
    private LocalDateTime dataHora;

    public Feedback() {
    }

    public Feedback(int id, Pessoa usuario, String mensagem, LocalDateTime dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.mensagem = mensagem;
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
