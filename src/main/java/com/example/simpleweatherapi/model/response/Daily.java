package com.example.simpleweatherapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Daily {
    @JsonProperty("time")
    private List<String> time;

    @JsonProperty("weather_code")
    private List<Integer> weatherCode;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperature2mMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperature2mMin;

    @JsonProperty("sunshine_duration")
    private List<Double> sunshineDuration;
}
