package org.example.repositories;

import org.example.entitiesfinal.Pessoa;
import org.example.entitiesfinal.Trajeto;
import org.example.entitiesfinal.MeioDeTransporte;
import org.example.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrajetoRepository {

    public void salvar(Trajeto trajeto) throws SQLException {
        String sql = "INSERT INTO trajetos (pessoa_id, origem, destino, distancia_km, pontos, meio_de_transporte) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trajeto.getPessoa().getId());
            stmt.setString(2, trajeto.getOrigem());
            stmt.setString(3, trajeto.getDestino());
            stmt.setDouble(4, trajeto.getDistanciaKm());
            stmt.setInt(5, trajeto.getPontos());
            stmt.setString(6, trajeto.getMeioDeTransporte().toString());

            stmt.executeUpdate();
        }
    }


    public Trajeto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM trajetos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Trajeto trajeto = new Trajeto();
                trajeto.setId(rs.getInt("id"));
                trajeto.setDistanciaKm(rs.getDouble("distancia_km"));
                trajeto.setPontos(rs.getInt("pontos"));
                trajeto.setMeioDeTransporte(MeioDeTransporte.valueOf(rs.getString("meio_de_transporte")));
                return trajeto;
            }
        }
        return null;
    }

    public void atualizar(Trajeto trajeto) throws SQLException {
        String sql = "UPDATE trajetos SET origem = ?, destino = ?, distancia_km = ?, pontos = ?, meio_de_transporte = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trajeto.getPessoa().getNome());
            stmt.setString(2, trajeto.getPessoa().getEmail());
            stmt.setDouble(3, trajeto.getDistanciaKm());
            stmt.setInt(4, trajeto.getPontos());
            stmt.setString(5, trajeto.getMeioDeTransporte().toString());
            stmt.setInt(6, trajeto.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM trajetos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void excluirTrajetosPorPessoaId(int pessoaId) throws SQLException {
        String sql = "DELETE FROM trajetos WHERE pessoa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            stmt.executeUpdate();
        }
    }
    public List<Trajeto> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM trajetos";
        List<Trajeto> trajetos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trajeto trajeto = new Trajeto();
                trajeto.setId(rs.getInt("id"));
                trajeto.setDistanciaKm(rs.getDouble("distancia_km"));
                trajeto.setPontos(rs.getInt("pontos"));
                trajeto.setMeioDeTransporte(MeioDeTransporte.valueOf(rs.getString("meio_de_transporte")));

                trajetos.add(trajeto);
            }
        }
        return trajetos;
    }
    public List<Trajeto> buscarPorPessoaId(int pessoaId) throws SQLException {
        String sql = "SELECT * FROM trajetos WHERE pessoa_id = ?";
        List<Trajeto> trajetos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trajeto trajeto = new Trajeto();
                trajeto.setId(rs.getInt("id"));
                trajeto.setDistanciaKm(rs.getDouble("distancia_km"));
                trajeto.setPontos(rs.getInt("pontos"));
                trajeto.setMeioDeTransporte(MeioDeTransporte.valueOf(rs.getString("meio_de_transporte")));

                trajetos.add(trajeto);
            }
        }
        return trajetos;
    }
}




