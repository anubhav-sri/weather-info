package com.tenera.weather.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class WeatherInfo {
    private BigDecimal temperature;
    private BigDecimal pressure;
    private WeatherType weatherType;

}
