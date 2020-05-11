package com.tenera.weather.models;

import com.tenera.weather.models.WeatherInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class WeatherInfoHistoryDetails {
    private BigDecimal averageTemperature;
    private BigDecimal averagePressure;
    private List<WeatherInfo> weatherInfos;
}

