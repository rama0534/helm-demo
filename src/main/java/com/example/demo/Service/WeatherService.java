package com.example.demo.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {
    private static final String BASE_URL = "https://jsonmock.hackerrank.com/api/weather";
    public  String getTemperatureByCity(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String urlString = String.format(BASE_URL + "?name=%s", encodedCity);
            JSONObject json = getAPIResponse(urlString);
            JSONArray data = json.getJSONArray("data");
            if (data.isEmpty()) {
                System.out.println("City not found: " + city);
                return null;
            }
            String weather = data.getJSONObject(0).getString("weather");
            return weather.replaceAll("[^\\-0-9]", ""); // keep "-" and digits only
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getAPIResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        )) {
            StringBuilder content = new StringBuilder();
            String lineRead;
            while ((lineRead = bufferedReader.readLine()) != null) {
                content.append(lineRead);
            }
            return new JSONObject(content.toString());
        } finally {
            connection.disconnect();
        }
    }
}
