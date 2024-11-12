package org.example.entities;

import org.example.entities.Coordenadas;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeocodingUtils {
    private static final String API_KEY = "AIzaSyD8vtpJx01z5ffBFHBhZMbgqUeYqtMZKH4";

    public static Coordenadas geocodeEndereco(String endereco) {
        try {
            String urlEndereco = "https://maps.googleapis.com/maps/api/geocode/json?address="
                    + java.net.URLEncoder.encode(endereco, "UTF-8")
                    + "&key=" + API_KEY;

            URL url = new URL(urlEndereco);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder resposta = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                resposta.append(inputLine);
            }
            in.close();

            JSONObject jsonObj = new JSONObject(resposta.toString());
            if ("OK".equals(jsonObj.getString("status"))) {
                JSONObject location = jsonObj.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                return new Coordenadas(lat, lng);
            } else {
                System.out.println("Erro na geocodificação: " + jsonObj.getString("status"));
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exceção ao geocodificar endereço: " + e.getMessage());
            return null;
        }
    }

    public static double calcularDistancia(Coordenadas origem, Coordenadas destino) {
        double lat1 = origem.getLatitude();
        double lon1 = origem.getLongitude();
        double lat2 = destino.getLatitude();
        double lon2 = destino.getLongitude();
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
