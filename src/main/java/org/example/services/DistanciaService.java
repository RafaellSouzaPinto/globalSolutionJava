package org.example.services;

import org.example.entities.Trajeto;
import org.example.entities.Pessoa;
import org.example.infrastructure.ConnectionFactory;
import org.example.repositories.TrajetoRepo;
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
    private final TrajetoRepo trajetoRepository;
    private final PessoaRepo pessoaRepo;

    public DistanciaService(TrajetoRepo trajetoRepository, PessoaRepo pessoaRepo) {
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


    public Trajeto registrarTrajeto(int pessoaId, String origem, String destino, String meioDeTransporte) throws Exception {
        Pessoa pessoa = pessoaRepo.getPessoaById(pessoaId);
        if (pessoa == null) {
            throw new Exception("Pessoa com ID " + pessoaId + " não encontrada.");
        }

        // Obter coordenadas de origem e destino
        double[] origemCoord = geocodeAddress(origem);
        double[] destinoCoord = geocodeAddress(destino);

        // Calcular a distância em quilômetros
        double distanciaKm = calculateDistance(origemCoord, destinoCoord);
        System.out.println("Distância calculada: " + distanciaKm + " km");  // Debug

        Trajeto trajeto = new Trajeto();
        trajeto.setPessoa(pessoa);
        trajeto.setOrigem(origem);
        trajeto.setDestino(destino);
        trajeto.setDistanciaKm(distanciaKm); // Definindo a distância no trajeto
        trajeto.setMeioDeTransporte(meioDeTransporte);

        // Calcula os pontos com base no plano
        int pontosBase = (int) Math.floor(distanciaKm);
        if ("Plano Super Verdí".equalsIgnoreCase(pessoa.getPlanos())) {
            trajeto.setPontos((int) Math.floor(pontosBase * 1.5));
        } else {
            trajeto.setPontos(pontosBase);
        }
        System.out.println("Pontos calculados: " + trajeto.getPontos()); // Debug

        trajetoRepository.salvar(trajeto);

        // Atualizar dados da pessoa
        double distanciaAcumulada = trajetoRepository.getTotalDistanciaByPessoaId(pessoaId);
        pessoa.setDistanciaAcumulada(distanciaAcumulada);

        int pontosTotais = trajetoRepository.getTotalPontosByPessoaId(pessoaId);
        int novosCreditos = pontosTotais / PONTOS_PARA_CREDITO;
        int pontosRestantes = pontosTotais % PONTOS_PARA_CREDITO;

        String sqlUpdate = "UPDATE pessoas SET pontos = ?, creditos = ?, distancia_acumulada = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

            stmtUpdate.setInt(1, pontosRestantes);
            stmtUpdate.setInt(2, novosCreditos);
            stmtUpdate.setDouble(3, distanciaAcumulada);
            stmtUpdate.setInt(4, pessoaId);
            stmtUpdate.executeUpdate();
        }

        System.out.println("Trajeto registrado com sucesso! Pontos acumulados: " + pontosTotais);

        return trajeto; // Retorna o trajeto com distância e pontos calculados
    }


}

