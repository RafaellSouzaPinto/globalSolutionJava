package org.example.entities;

import java.math.BigDecimal;

public class Plano {

    private int id;
    private String nome;
    private BigDecimal preco;
    private String beneficios;

    public Plano() {
    }

    public Plano(int id, String nome, BigDecimal preco, String beneficios) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.beneficios = beneficios;
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }
    @Override
    public String toString() {
        return "Plano {" +
                "ID=" + id +
                ", Nome='" + nome + '\'' +
                ", Preço=" + preco +
                ", Benefícios='" + beneficios + '\'' +
                '}';
    }
    public void aplicarDesconto(BigDecimal percentualDesconto) {
        if (percentualDesconto.compareTo(BigDecimal.ZERO) > 0 && percentualDesconto.compareTo(new BigDecimal("100")) < 0) {
            BigDecimal desconto = preco.multiply(percentualDesconto).divide(new BigDecimal("100"));
            this.preco = preco.subtract(desconto);
        }
    }


}
