package org.example.entities;

public class MeioTransporte {

    private int id;
    private String nome;

    public MeioTransporte() {
    }

    public MeioTransporte(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
