package com.tenera.weather.services.clients.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
public class ExternalWeather {
    private String main;
}

