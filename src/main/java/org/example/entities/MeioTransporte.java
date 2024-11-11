package org.example.entities;

public class MeioTransporte {

    private int id;
    private String nome;
    private float velocidadeMedia;

    public MeioTransporte() {
    }

    public MeioTransporte(int id, String nome, float velocidadeMedia) {
        this.id = id;
        this.nome = nome;
        this.velocidadeMedia = velocidadeMedia;
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

    public float getVelocidadeMedia() {
        return velocidadeMedia;
    }

    public void setVelocidadeMedia(float velocidadeMedia) {
        this.velocidadeMedia = velocidadeMedia;
    }
}
