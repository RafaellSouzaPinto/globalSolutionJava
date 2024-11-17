package org.example.repositories;

import org.example.entities.Trajeto;
import org.example.infrastructure.ConnectionFactory;
import org.example.infrastructure.Log4jLogger;
import org.example.services.TrajetoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrajetoRepo {

    private static final Log4jLogger logger = new Log4jLogger(TrajetoRepo.class);
    private final TrajetoService trajetoService;

    public TrajetoRepo() {
        this.trajetoService = new TrajetoService();
    }

    public void salvar(Trajeto trajeto) throws SQLException {
        logger.info("Tentando salvar trajeto: " + trajeto.getOrigem() + " -> " + trajeto.getDestino());
        if (!trajetoService.validarCadastroTrajeto(trajeto)) {
            logger.warn("Dados inválidos ao tentar salvar trajeto.");
            throw new IllegalArgumentException("Dados de trajeto inválidos ou incompletos.");
        }

        String sql = "INSERT INTO trajetos (pessoa_id, origem, destino, distancia_km, pontos, meio_de_transporte) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trajeto.getPessoa().getId());
            stmt.setString(2, trajeto.getOrigem());
            stmt.setString(3, trajeto.getDestino());
            stmt.setDouble(4, trajeto.getDistanciaKm());
            stmt.setInt(5, trajeto.getPontos());
            stmt.setString(6, trajeto.getMeioDeTransporte());

            stmt.executeUpdate();
            logger.info("Trajeto salvo com sucesso: " + trajeto.getOrigem() + " -> " + trajeto.getDestino());
        } catch (SQLException e) {
            logger.error("Erro ao salvar trajeto: " + trajeto.getOrigem() + " -> " + trajeto.getDestino(), e);
            throw e;
        }
    }


    public Trajeto buscarPorId(int id) throws SQLException {
        logger.info("Buscando trajeto por ID: " + id);
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
                trajeto.setMeioDeTransporte(rs.getString("meio_de_transporte"));
                logger.info("Trajeto encontrado para ID: " + id);
                return trajeto;
            } else {
                logger.warn("Nenhum trajeto encontrado para ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar trajeto por ID: " + id, e);
            throw e;
        }
    }

    public void atualizar(Trajeto trajeto) throws SQLException {
        logger.info("Atualizando trajeto ID: " + trajeto.getId());
        if (!trajetoService.validarCadastroTrajeto(trajeto)) {
            logger.warn("Dados inválidos ao tentar atualizar trajeto ID: " + trajeto.getId());
            throw new IllegalArgumentException("Dados de trajeto inválidos ou incompletos.");
        }

        String sql = "UPDATE trajetos SET origem = ?, destino = ?, distancia_km = ?, pontos = ?, meio_de_transporte = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trajeto.getOrigem());
            stmt.setString(2, trajeto.getDestino());
            stmt.setDouble(3, trajeto.getDistanciaKm());
            stmt.setInt(4, trajeto.getPontos());
            stmt.setString(5, trajeto.getMeioDeTransporte());
            stmt.setInt(6, trajeto.getId());

            stmt.executeUpdate();
            logger.info("Trajeto atualizado com sucesso ID: " + trajeto.getId());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar trajeto ID: " + trajeto.getId(), e);
            throw e;
        }
    }

    public void deletar(int id) throws SQLException {
        logger.info("Deletando trajeto ID: " + id);
        String sql = "DELETE FROM trajetos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Trajeto deletado com sucesso ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao deletar trajeto ID: " + id, e);
            throw e;
        }
    }

    public void excluirTrajetosPorPessoaId(int pessoaId) throws SQLException {
        logger.info("Excluindo todos os trajetos para pessoa ID: " + pessoaId);
        String sql = "DELETE FROM trajetos WHERE pessoa_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            stmt.executeUpdate();
            logger.info("Todos os trajetos excluídos para pessoa ID: " + pessoaId);
        } catch (SQLException e) {
            logger.error("Erro ao excluir trajetos para pessoa ID: " + pessoaId, e);
            throw e;
        }
    }
    public List<Trajeto> buscarTodos() throws SQLException {
        logger.info("Buscando todos os trajetos");
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
                trajeto.setMeioDeTransporte(rs.getString("meio_de_transporte"));
                trajetos.add(trajeto);
            }
            logger.info("Total de trajetos encontrados: " + trajetos.size());
        } catch (SQLException e) {
            logger.error("Erro ao buscar todos os trajetos", e);
            throw e;
        }
        return trajetos;
    }

    public List<Trajeto> buscarPorPessoaId(int pessoaId) throws SQLException {
        logger.info("Buscando trajetos para pessoa ID: " + pessoaId);
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
                trajeto.setMeioDeTransporte(rs.getString("meio_de_transporte"));
                trajeto.setOrigem(rs.getString("origem"));
                trajeto.setDestino(rs.getString("destino"));
                trajetos.add(trajeto);
            }
            logger.info("Total de trajetos encontrados para pessoa ID: " + pessoaId + ": " + trajetos.size());
        } catch (SQLException e) {
            logger.error("Erro ao buscar trajetos para pessoa ID: " + pessoaId, e);
            throw e;
        }
        return trajetos;
    }

    public int getTotalPontosByPessoaId(int pessoaId) {
        logger.info("Calculando total de pontos para pessoa ID: " + pessoaId);
        String sql = "SELECT SUM(pontos) FROM trajetos WHERE pessoa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int totalPontos = rs.getInt(1);
                logger.info("Total de pontos calculado para pessoa ID: " + pessoaId + ": " + totalPontos);
                return totalPontos;
            } else {
                logger.warn("Nenhum ponto encontrado para pessoa ID: " + pessoaId);
                return 0;
            }
        } catch (Exception e) {
            logger.error("Erro ao calcular total de pontos para pessoa ID: " + pessoaId, e);
            return 0;
        }
    }
    public double getTotalDistanciaByPessoaId(int pessoaId) {
        logger.info("Calculando total de distância para pessoa ID: " + pessoaId);
        String sql = "SELECT SUM(distancia_km) FROM trajetos WHERE pessoa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalDistancia = rs.getDouble(1);
                logger.info("Total de distância calculada para pessoa ID: " + pessoaId + ": " + totalDistancia);
                return totalDistancia;
            } else {
                logger.warn("Nenhuma distância encontrada para pessoa ID: " + pessoaId);
                return 0.0;
            }
        } catch (Exception e) {
            logger.error("Erro ao calcular total de distância para pessoa ID: " + pessoaId, e);
            return 0.0;
        }
    }


}




