package com.tenera.weather.services.clients.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WeatherCondition {
    @JsonProperty("weather")
    private List<ExternalWeather> weather;
    @JsonProperty("main")
    private MainContent mainContent;

}
