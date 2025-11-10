package com.example.academy.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.openweather.apiKey}")
    private String apiKey;

    // Returns WeatherDTO with temp in °C using OpenWeather "weather" endpoint
    public WeatherDTO getForCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}&units=metric";
        RestTemplate rt = new RestTemplate();

        @SuppressWarnings("unchecked")
        Map<String, Object> json = rt.getForObject(url, Map.class, Map.of("city", city, "key", apiKey));

        if (json == null) return new WeatherDTO(city, null, "unavailable");

        // main.temp as number (already in °C because units=metric)
        Double tempC = null;
        Object mainObj = json.get("main");
        if (mainObj instanceof Map<?, ?> main) {
            Object t = main.get("temp");
            if (t instanceof Number n) tempC = n.doubleValue();
        }

        // weather[0].description
        String description = null;
        Object weatherObj = json.get("weather");
        if (weatherObj instanceof List<?> list && !list.isEmpty()) {
            Object first = list.get(0);
            if (first instanceof Map<?, ?> w) {
                Object d = w.get("description");
                if (d != null) description = d.toString();
            }
        }

        return new WeatherDTO(city, tempC, description);
    }
}
