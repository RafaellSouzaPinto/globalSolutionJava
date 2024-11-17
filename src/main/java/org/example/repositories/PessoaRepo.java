package org.example.repositories;

import org.example.entities.Pessoa;
import org.example.infrastructure.ConnectionFactory;
import org.example.infrastructure.Log4jLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepo {

    private static final Log4jLogger logger = new Log4jLogger(PessoaRepo.class);


    public boolean cadastrarPessoa(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoas (nome, cpf, email, senha, planos) VALUES (?, ?, ?, ?, ?)";
        logger.info("Tentando cadastrar uma nova pessoa: " + pessoa.getNome());
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getSenha());
            stmt.setString(5, pessoa.getPlanos());

            int rowsAffected = stmt.executeUpdate();
            logger.info("Pessoa cadastrada com sucesso: " + pessoa.getNome());
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Erro ao cadastrar pessoa: " + pessoa.getNome(), e);
            throw e;
        }
    }



    public Pessoa login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM pessoas WHERE email = ? AND senha = ?";
        logger.info("Tentando login para o email: " + email);
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
                logger.info("Login realizado com sucesso para o email: " + email);
                return pessoa;
            } else {
                logger.warn("Login falhou para o email: " + email);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erro ao tentar login para o email: " + email, e);
            throw e;
        }
    }

    public boolean alterarSenha(int pessoaId, String senhaAtual, String novaSenha) throws SQLException {
        logger.info("Tentando alterar senha para o ID da pessoa: " + pessoaId);
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
                        logger.info("Senha alterada com sucesso para o ID da pessoa: " + pessoaId);
                        return rowsAffected > 0;
                    } else {
                        String errorMessage = "A nova senha deve ter no mínimo 8 caracteres.";
                        logger.warn(errorMessage);
                        throw new IllegalArgumentException(errorMessage);
                    }
                } else {
                    logger.warn("Senha atual incorreta para o ID da pessoa: " + pessoaId);
                    return false;
                }
            } else {
                logger.warn("Pessoa não encontrada para o ID: " + pessoaId);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Erro ao alterar senha para o ID da pessoa: " + pessoaId, e);
            throw e;
        }
    }


    public boolean excluirConta(int pessoaId) throws SQLException {
        logger.info("Tentando excluir conta para o ID da pessoa: " + pessoaId);
        TrajetoRepo trajetoRepo = new TrajetoRepo();

        try {
            trajetoRepo.excluirTrajetosPorPessoaId(pessoaId);

            String sql = "DELETE FROM pessoas WHERE id = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, pessoaId);
                int rowsAffected = stmt.executeUpdate();
                logger.info("Conta excluída com sucesso para o ID da pessoa: " + pessoaId);
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            logger.error("Erro ao excluir conta para o ID da pessoa: " + pessoaId, e);
            throw e;
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
                pessoa.setPlanos(rs.getString("planos"));
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }





}