package com.example.academy.weather;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174","http://localhost:5176","http://localhost:5177","http://localhost:5178"})
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public WeatherDTO get(@RequestParam String city) {
        return service.getForCity(city);
    }
}
