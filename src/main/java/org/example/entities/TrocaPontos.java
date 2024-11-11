package org.example.entities;

import java.time.LocalDateTime;

public class TrocaPontos {
    private int id;
    private Usuario usuario;
    private Recompensa recompensa;
    private LocalDateTime dataHora;

    public TrocaPontos() {
    }

    public TrocaPontos(int id, Usuario usuario, Recompensa recompensa, LocalDateTime dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.recompensa = recompensa;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }
}
