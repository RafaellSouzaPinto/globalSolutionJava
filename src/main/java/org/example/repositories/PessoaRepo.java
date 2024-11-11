package org.example.repositories;

import org.example.entities.Pessoa;
import org.example.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaRepo {
    // Método para cadastrar uma nova pessoa
    public boolean cadastrarPessoa(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoas (nome, cpf, email, senha) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getSenha());

            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("CPF: " + pessoa.getCpf());
            System.out.println("Email: " + pessoa.getEmail());
            System.out.println("Senha: " + pessoa.getSenha());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a inserção foi bem-sucedida
        }
    }


    public Pessoa login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM pessoas WHERE email = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setSenha(rs.getString("senha"));
                return pessoa;
            } else {
                return null;
            }
        }
    }
}