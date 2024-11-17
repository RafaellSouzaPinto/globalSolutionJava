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
    @Override
    public String toString() {
        return "MeioTransporte {" +
                "ID=" + id +
                ", Nome='" + nome + '\'' +
                '}';
    }
    public String gerarCodigoIdentificador() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalStateException("Nome do meio de transporte não pode estar vazio para gerar o identificador.");
        }
        String slug = nome.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        return slug + "-" + id;
    }


}
