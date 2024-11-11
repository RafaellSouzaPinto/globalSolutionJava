package org.example;

import org.example.entities.Pessoa;
import org.example.repositories.PessoaRepo;

import java.sql.SQLException;

public class teste {
    public static void main(String[] args) {
        PessoaRepo pessoaRepo = new PessoaRepo();

        Pessoa primeiraPessoa = new Pessoa(18, "rafinha", "82764984734", "rafinha.silva@example.com", "rafinha");
        try {
            boolean cadastrado = pessoaRepo.cadastrarPessoa(primeiraPessoa);
            if (cadastrado) {
                System.out.println("Primeiro usuário cadastrado com sucesso!");
            } else {
                System.out.println("Falha no cadastro do primeiro usuário.");
            }

            Pessoa usuarioLogado = pessoaRepo.login("rafinha.silva@example.com", "rafinha");
            if (usuarioLogado != null) {
                System.out.println("Login bem-sucedido!");
                System.out.println("Bem-vindo, " + usuarioLogado.getNome());
            } else {
                System.out.println("Falha no login. Verifique as credenciais.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
