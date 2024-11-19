package org.example.entities;

public class Recompensa {
    private int id;
    private Pessoa nome;
    private String descricao;
    private int pontosNecessarios;

    public Recompensa() {
    }

    public Recompensa(int id, Pessoa nome, String descricao, int pontosNecessarios) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.pontosNecessarios = pontosNecessarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getNome() {
        return nome;
    }

    public void setNome(Pessoa nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPontosNecessarios() {
        return pontosNecessarios;
    }

    public void setPontosNecessarios(int pontosNecessarios) {
        this.pontosNecessarios = pontosNecessarios;
    }

    public void reduzirPontosNecessarios(int quantidade) {
        if (quantidade > 0 && quantidade <= this.pontosNecessarios) {
            this.pontosNecessarios -= quantidade;
        }
    }

    @Override
    public String toString() {
        return "Recompensa{" +
                "id=" + id +
                ", nome=" + nome +
                ", descricao='" + descricao + '\'' +
                ", pontosNecessarios=" + pontosNecessarios +
                '}';
    }
}
