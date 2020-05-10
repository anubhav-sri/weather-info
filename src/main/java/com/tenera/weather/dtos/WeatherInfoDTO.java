package com.tenera.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class WeatherInfoDTO {
    private BigDecimal temp;
    private BigDecimal pressure;
    private Boolean umbrella;
}
