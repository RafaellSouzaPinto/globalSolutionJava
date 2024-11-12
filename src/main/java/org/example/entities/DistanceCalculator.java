package org.example.entities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DistanceCalculator {

    private static final String GOOGLE_API_KEY = "AIzaSyD8vtpJx01z5ffBFHBhZMbgqUeYqtMZKH4";

    public static String getCoordinates(String address) throws Exception {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress + "&key=" + GOOGLE_API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject location = json.getAsJsonArray("results").get(0)
                .getAsJsonObject().getAsJsonObject("geometry")
                .getAsJsonObject("location");

        String lat = location.get("lat").getAsString();
        String lng = location.get("lng").getAsString();
        return lat + "," + lng;
    }

    public static double calculateDistance(String originCoords, String destCoords) {
        String[] origin = originCoords.split(",");
        String[] dest = destCoords.split(",");

        double lat1 = Double.parseDouble(origin[0]);
        double lon1 = Double.parseDouble(origin[1]);
        double lat2 = Double.parseDouble(dest[0]);
        double lon2 = Double.parseDouble(dest[1]);

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite o endereço de origem: ");
            String originAddress = scanner.nextLine();
            System.out.print("Digite o endereço de destino: ");
            String destinationAddress = scanner.nextLine();

            String originCoords = getCoordinates(originAddress);
            String destCoords = getCoordinates(destinationAddress);

            double distanceKm = calculateDistance(originCoords, destCoords);
            int points = (int) distanceKm;
            int carbonCredits = points / 4200;

            System.out.printf("\nDistância entre '%s' e '%s': %.2f km%n", originAddress, destinationAddress, distanceKm);
            System.out.printf("Pontuação ganha: %d pontos%n", points);
            System.out.printf("Créditos de carbono obtidos: %d créditos%n", carbonCredits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
