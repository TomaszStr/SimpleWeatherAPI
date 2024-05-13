package com.example.simpleweatherapi.model;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherForecast {
    private List<String> time;
    private List<String> weatherCode;
    private List<Double> temperatureMax;
    private List<Double> temperatureMin;
    private List<Double> energyProduced;
    private List<String> forecastUnits;
}
