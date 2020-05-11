package com.tenera.weather.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class WeatherInfoHistoryDetailsDto {
    @JsonProperty("avg_temp")
    private BigDecimal averageTemperature;
    @JsonProperty("avg_pressure")
    private BigDecimal averagePressure;
    @JsonProperty("history")
    private List<WeatherInfoDTO> weatherInfos;
}

