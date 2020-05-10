package com.tenera.weather.services;

import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.models.WeatherType;
import com.tenera.weather.services.clients.WeatherDataClient;
import com.tenera.weather.services.clients.models.ExternalWeather;
import com.tenera.weather.services.clients.models.MainContent;
import com.tenera.weather.services.clients.models.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherDataClient weatherDataClient;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(weatherDataClient);
    }

    @Test
    void shouldGetWeatherDataFromTheClient() {
        when(weatherDataClient.getWeatherCondition("Berlin")).thenReturn(buildWeatherCondition());
        WeatherInfo weatherInfo = weatherService.getWeatherInformationForCity("Berlin");

        assertThat(weatherInfo).isEqualTo(new WeatherInfo(BigDecimal.ONE, BigDecimal.TEN, WeatherType.RAINY));
    }


    private WeatherCondition buildWeatherCondition() {
        return WeatherCondition.builder()
                .mainContent(new MainContent(BigDecimal.ONE, BigDecimal.TEN))
                .weather(List.of(new ExternalWeather("Rainy")))
                .build();
    }
}