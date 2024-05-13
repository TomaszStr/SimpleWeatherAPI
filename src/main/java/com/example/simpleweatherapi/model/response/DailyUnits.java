package com.example.simpleweatherapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyUnits {
    @JsonProperty("time")
    private String time;

    @JsonProperty("weather_code")
    private String weatherCode;

    @JsonProperty("temperature_2m_max")
    private String temperature2mMax;

    @JsonProperty("temperature_2m_min")
    private String temperature2mMin;

    @JsonProperty("sunshine_duration")
    private String sunshineDuration;
}
