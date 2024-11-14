package org.example.repositories;

import org.example.entitiesfinal.Pessoa;
import org.example.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepo {
    public boolean cadastrarPessoa(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoas (nome, cpf, email, senha, planos) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getSenha());
            stmt.setString(5, pessoa.getPlanos());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
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
                return pessoa;
            } else {

                return null;
            }
        }
    }

    public boolean alterarSenha(int pessoaId, String senhaAtual, String novaSenha) throws SQLException {
        String sqlSelect = "SELECT senha FROM pessoas WHERE id = ?";
        String sqlUpdate = "UPDATE pessoas SET senha = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

            stmtSelect.setInt(1, pessoaId);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                String senhaBanco = rs.getString("senha");

                if (senhaBanco.equals(senhaAtual)) {
                    if (novaSenha.length() >= 8) {
                        stmtUpdate.setString(1, novaSenha);
                        stmtUpdate.setInt(2, pessoaId);

                        int rowsAffected = stmtUpdate.executeUpdate();
                        return rowsAffected > 0;
                    } else {
                        throw new IllegalArgumentException("A nova senha deve ter no mínimo 8 caracteres.");
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }



    // Método para excluir conta da pessoa e seus trajetos associados
    public boolean excluirConta(int pessoaId) throws SQLException {
        TrajetoRepository trajetoRepo = new TrajetoRepository();

        // Primeiro, exclua todos os trajetos da pessoa
        trajetoRepo.excluirTrajetosPorPessoaId(pessoaId);

        // Em seguida, exclua a própria pessoa
        String sql = "DELETE FROM pessoas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean emailExiste(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pessoas WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean cpfExiste(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pessoas WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
    public Pessoa getPessoaById(int id) {
        String sql = "SELECT id, nome, cpf, senha, email, pontos, creditos, distancia_acumulada, planos FROM pessoas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setPontos(rs.getInt("pontos"));
                pessoa.setCreditos(rs.getInt("creditos"));
                pessoa.setDistanciaAcumulada(rs.getDouble("distancia_acumulada"));
                pessoa.setPlanos(rs.getString("planos")); // Definindo o campo "planos"
                return pessoa;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Pessoa> getAllPessoas() throws SQLException {
        String sql = "SELECT * FROM pessoas";
        List<Pessoa> pessoas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setPlanos(rs.getString("planos")); // Definindo o campo "planos"
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }





}