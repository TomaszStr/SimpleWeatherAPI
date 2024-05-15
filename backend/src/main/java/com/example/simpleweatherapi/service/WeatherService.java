package com.example.simpleweatherapi.service;

import com.example.simpleweatherapi.model.*;
import com.example.simpleweatherapi.model.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherForecast getWeatherForecast(float latitude, float longitude) {
        //check those values

        //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&daily=weather_code,temperature_2m_max,temperature_2m_min,sunshine_duration&timezone=auto
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=weather_code,temperature_2m_max,temperature_2m_min,sunshine_duration&timezone=auto";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        // Save API response to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;
        try {
            weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        WeatherForecast weatherForecast = createWeatherForecast(weatherResponse);

        return weatherForecast;
    }

    private WeatherForecast createWeatherForecast(WeatherResponse weatherForecastResponse) {

        WeatherForecast weatherForecast = WeatherForecast.builder()
                .time(GetReformattedDate(weatherForecastResponse.getDaily().getTime()))
                .weatherCode(GetWeatherTypes(weatherForecastResponse.getDaily().getWeatherCode()))
                .temperatureMax(weatherForecastResponse.getDaily().getTemperature2mMax())
                .temperatureMin(weatherForecastResponse.getDaily().getTemperature2mMin())
                .energyProduced(GetEnergyProduced(weatherForecastResponse.getDaily().getSunshineDuration()))
                .forecastUnits(Arrays.asList(new String[]{"","","[°C]","[°C]","[kWh]"}))
                .build();

        return weatherForecast;
    }

    private List<Double> GetEnergyProduced(List<Double> sunshineDuration){
        List<Double> energyProduced = new ArrayList<>();
        for(Double dailySunshineDuration : sunshineDuration){
            energyProduced.add(CalculateEnergy(dailySunshineDuration));
        }
        return energyProduced;
    }

    private List<String> GetReformattedDate(List<String> dates){
        List<String> reformattedDate = new ArrayList<>();
        for(String date : dates){
            reformattedDate.add(ReformatDate(date));
        }
        return reformattedDate;
    }
    private String ReformatDate(String dateString){
        String outDate = dateString;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(dateString);

            outDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }
    private double CalculateEnergy(double sunshineDuration){
        // result in kWh
        // 1h = 3600s, 1k = 1000
        return ((sunshineDuration/3600) * 0.2 * 2500)/1000;
    }

    private List<String> GetWeatherTypes(List<Integer> wmoCodes){
        List<String> weatherTypes = new ArrayList<>();
        for(Integer wmoCode : wmoCodes){
            weatherTypes.add(GetWeatherType(wmoCode));
        }
        return weatherTypes;
    }

    private String GetWeatherType(int wmoCode){
        switch (wmoCode) {
            case 0:
                return "Sun";
            case 1,2,3:
                return "Cloud";
            case 45,48:
                return "Fog";
            case 51,53,55,56,57,61,63,65,66,67,80,81,82:
                return "Rain";
            case 71,73,75,77,85,86:
                return "Snow";
            case 95,96,99:
                return "Thunderstorm";
            default:
                return "Unknown";
        }
    }

    boolean CheckLatitude(float latitude){
        return latitude >= -90 && latitude <= 90;
    }
    boolean CheckLongitude(float longitude){
        return longitude >= -180 && longitude <= 180;
    }
}

