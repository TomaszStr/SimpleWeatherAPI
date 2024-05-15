package com.example.simpleweatherapi.controller;

import com.example.simpleweatherapi.model.WeatherForecast;
import com.example.simpleweatherapi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherForecastController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @CrossOrigin(originPatterns = "http://localhost:*")
    @GetMapping("/weather")
    public ResponseEntity<WeatherForecast> getWeatherForecast(
            @RequestParam("latitude")  float latitude,
            @RequestParam("longitude") float longitude) {
        if(latitude < -90 || latitude > 90 || longitude > 180 || longitude < -180)
            return ResponseEntity.badRequest().body(null);
        WeatherForecast weatherForecast = weatherService.getWeatherForecast(latitude, longitude);
        return ResponseEntity.ok().body(weatherForecast);
    }
}
