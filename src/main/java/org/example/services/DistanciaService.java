package org.example.services;

import org.example.entitiesfinal.Trajeto;
import org.example.entitiesfinal.MeioDeTransporte;
import org.example.entitiesfinal.Pessoa;
import org.example.infrastructure.ConnectionFactory;
import org.example.repositories.TrajetoRepository;
import org.example.repositories.PessoaRepo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class DistanciaService {

    private static final String API_KEY = "AIzaSyD8vtpJx01z5ffBFHBhZMbgqUeYqtMZKH4";
    private static final int PONTOS_PARA_CREDITO = 4200;
    private final TrajetoRepository trajetoRepository;
    private final PessoaRepo pessoaRepo;

    public DistanciaService(TrajetoRepository trajetoRepository, PessoaRepo pessoaRepo) {
        this.trajetoRepository = trajetoRepository;
        this.pessoaRepo = pessoaRepo;
    }

    public double[] geocodeAddress(String address) throws Exception {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress + "&key=" + API_KEY;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(content.toString());

        // Handle potential API errors
        String status = json.getString("status");
        if (!status.equals("OK")) {
            String errorMessage = json.optString("error_message", "Unknown error");
            throw new Exception("Geocoding API error: " + status + " - " + errorMessage);
        }

        if (json.getJSONArray("results").length() > 0) {
            JSONObject location = json.getJSONArray("results").getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location");
            return new double[]{location.getDouble("lat"), location.getDouble("lng")};
        } else {
            throw new Exception("Endereço não encontrado.");
        }
    }

    public double calculateDistance(double[] origin, double[] destination) {
        double earthRadius = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(destination[0] - origin[0]);
        double dLng = Math.toRadians(destination[1] - origin[1]);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(origin[0])) * Math.cos(Math.toRadians(destination[0]))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public void registrarTrajeto(int pessoaId, String origem, String destino, MeioDeTransporte meioDeTransporte) throws Exception {
        Pessoa pessoa = pessoaRepo.getPessoaById(pessoaId);
        if (pessoa == null) {
            throw new Exception("Pessoa com ID " + pessoaId + " não encontrada.");
        }

        double[] origemCoord;
        double[] destinoCoord;
        try {
            origemCoord = geocodeAddress(origem);
            destinoCoord = geocodeAddress(destino);
        } catch (Exception e) {
            throw new Exception("Erro ao geocodificar o endereço: " + e.getMessage());
        }

        double distanciaKm = calculateDistance(origemCoord, destinoCoord);

        // Create and save the new trajectory
        Trajeto trajeto = new Trajeto();
        trajeto.setPessoa(pessoa);
        trajeto.setOrigem(origem);
        trajeto.setDestino(destino);
        trajeto.setDistanciaKm(distanciaKm);
        trajeto.setMeioDeTransporte(meioDeTransporte);
        trajeto.setPontos((int) Math.floor(distanciaKm));

        trajetoRepository.salvar(trajeto);

        // Calculate total accumulated distance
        double distanciaAcumulada = trajetoRepository.getTotalDistanciaByPessoaId(pessoaId);
        pessoa.setDistanciaAcumulada(distanciaAcumulada);

        // Calculate total points based on accumulated distance
        int pontosTotais = (int) Math.floor(distanciaAcumulada);

        // Calculate credits
        int novosCreditos = pontosTotais / PONTOS_PARA_CREDITO;
        int pontosRestantes = pontosTotais % PONTOS_PARA_CREDITO;

        // Update user's points, credits, and accumulated distance in the database
        String sqlUpdate = "UPDATE pessoas SET pontos = ?, creditos = ?, distancia_acumulada = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

            stmtUpdate.setInt(1, pontosRestantes);
            stmtUpdate.setInt(2, novosCreditos);
            stmtUpdate.setDouble(3, distanciaAcumulada);
            stmtUpdate.setInt(4, pessoaId);
            stmtUpdate.executeUpdate();
        }

        System.out.println("Trajeto registrado com sucesso! Distância: " + distanciaKm + " km, Distância acumulada: " + distanciaAcumulada + " km, Pontos totais: " + pontosTotais);
    }
}

