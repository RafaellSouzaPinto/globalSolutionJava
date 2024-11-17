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
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", mensagem='" + mensagem + '\'' +
                ", dataHora=" + dataHora +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Feedback feedback = (Feedback) obj;
        return id == feedback.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }


}
