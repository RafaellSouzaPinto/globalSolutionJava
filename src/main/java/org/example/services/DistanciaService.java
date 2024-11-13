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
        if (json.getJSONArray("results").length() > 0) {
            JSONObject location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            return new double[]{location.getDouble("lat"), location.getDouble("lng")};
        } else {
            throw new Exception("Endereço não encontrado.");
        }
    }

    public double calculateDistance(double[] origin, double[] destination) {
        double earthRadius = 6371; // Raio médio da Terra em quilômetros
        double dLat = Math.toRadians(destination[0] - origin[0]);
        double dLng = Math.toRadians(destination[1] - origin[1]);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(origin[0])) * Math.cos(Math.toRadians(destination[0])) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public void registrarTrajeto(int pessoaId, String origem, String destino, MeioDeTransporte meioDeTransporte) throws Exception {
        Pessoa pessoa = pessoaRepo.getPessoaById(pessoaId);
        if (pessoa == null) {
            throw new Exception("Pessoa com ID " + pessoaId + " não encontrada.");
        }

        double[] origemCoord = geocodeAddress(origem);
        double[] destinoCoord = geocodeAddress(destino);
        double distanciaKm = calculateDistance(origemCoord, destinoCoord);

        int pontosGanhados = (int) distanciaKm;

        // Criar o objeto Trajeto com os dados calculados
        Trajeto trajeto = new Trajeto();
        trajeto.setPessoa(pessoa);
        trajeto.setMeioDeTransporte(meioDeTransporte);
        trajeto.setDistanciaKm(distanciaKm);
        trajeto.setPontos(pontosGanhados);
        trajeto.setOrigem(origem);
        trajeto.setDestino(destino);

        trajetoRepository.salvar(trajeto);

        String sqlUpdatePontos = "UPDATE pessoas SET pontos = pontos + ?, creditos = creditos + ? WHERE id = ?";
        int novosCreditos = (pessoa.getPontos() + pontosGanhados) / PONTOS_PARA_CREDITO;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtUpdatePontos = conn.prepareStatement(sqlUpdatePontos)) {

            stmtUpdatePontos.setInt(1, pontosGanhados);
            stmtUpdatePontos.setInt(2, novosCreditos);
            stmtUpdatePontos.setInt(3, pessoaId);
            stmtUpdatePontos.executeUpdate();
        }

        System.out.println("Trajeto registrado com sucesso! Distância: " + distanciaKm + " km, Pontos: " + pontosGanhados);
    }




}
