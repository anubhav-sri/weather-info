package com.tenera.weather.services;

import com.tenera.weather.models.WeatherHistory;
import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.models.WeatherType;
import com.tenera.weather.repositories.WeatherDataRepository;
import com.tenera.weather.services.clients.WeatherDataClient;
import com.tenera.weather.services.clients.models.ExternalWeather;
import com.tenera.weather.services.clients.models.MainContent;
import com.tenera.weather.services.clients.models.WeatherCondition;
import com.tenera.weather.services.clients.models.WeatherInfoHistoryDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherDataClient weatherDataClient;
    private WeatherService weatherService;
    @Mock
    private WeatherDataRepository weatherHistory;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(weatherDataClient, weatherHistory);
    }

    @Test
    void shouldGetWeatherDataFromTheClient() {
        when(weatherDataClient.getWeatherCondition("Berlin")).thenReturn(buildWeatherCondition());
        WeatherInfo weatherInfo = weatherService.getWeatherInformationForLocation("Berlin");

        assertThat(weatherInfo).isEqualTo(buildWeatherInfo());
    }

    @Test
    void shouldSaveTheWeatherDataFromRetrievedForACityWhenNoPreviousHistory() {
        when(weatherHistory.findById("Berlin")).thenReturn(Optional.empty());
        when(weatherDataClient.getWeatherCondition("Berlin")).thenReturn(buildWeatherCondition());
        WeatherInfo weatherInfo = weatherService.getWeatherInformationForLocation("Berlin");

        assertThat(weatherInfo).isEqualTo(buildWeatherInfo());
        ArgumentCaptor<WeatherHistory> whArgsCaptor = ArgumentCaptor.forClass(WeatherHistory.class);

        verify(weatherHistory).save(whArgsCaptor.capture());

        WeatherHistory actualWeatherHistory = whArgsCaptor.getValue();
        assertThat(actualWeatherHistory.getWeatherInfos()).hasSameElementsAs(buildWeatherHistory().getWeatherInfos());
        assertThat(actualWeatherHistory).isEqualToIgnoringGivenFields(buildWeatherHistory(), "weatherInfos");
    }

    @Test
    void shouldAddTheRetrievedInfoToTheHistory() {
        WeatherHistory expectedHistory = buildWeatherHistory();

        when(this.weatherHistory.findById("Berlin")).thenReturn(Optional.of(expectedHistory));
        when(weatherDataClient.getWeatherCondition("Berlin")).thenReturn(buildWeatherCondition());

        WeatherInfo weatherInfo = weatherService.getWeatherInformationForLocation("Berlin");

        assertThat(weatherInfo).isEqualTo(buildWeatherInfo());

        ArgumentCaptor<WeatherHistory> whArgsCaptor = ArgumentCaptor.forClass(WeatherHistory.class);
        verify(this.weatherHistory).save(whArgsCaptor.capture());

        WeatherHistory actualWeatherHistory = whArgsCaptor.getValue();
        expectedHistory.addInfo(buildWeatherInfo());

        assertThat(actualWeatherHistory.getWeatherInfos()).hasSameElementsAs(buildWeatherHistory().getWeatherInfos());
        assertThat(actualWeatherHistory).isEqualToIgnoringGivenFields(buildWeatherHistory(), "weatherInfos");

    }

    @Test
    void shouldGetTheHistoryOfLastFiveCallsToWeatherInfoForACity() {
        WeatherHistory expectedHistory = buildWeatherHistory();

        when(weatherHistory.findById("Berlin")).thenReturn(Optional.of(expectedHistory));

        WeatherInfoHistoryDetails weatherInfo = weatherService.getWeatherInfoHistory("Berlin");

        assertThat(weatherInfo).isEqualTo(new WeatherInfoHistoryDetails(BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(10.0),
                List.of(buildWeatherInfo())));

    }


    private WeatherInfo buildWeatherInfo() {
        return new WeatherInfo(BigDecimal.ONE, BigDecimal.TEN, WeatherType.RAINY);
    }

    private WeatherHistory buildWeatherHistory() {
        WeatherHistory history = new WeatherHistory("Berlin", "");
        history.addInfo(buildWeatherInfo());
        return history;
    }

    private WeatherCondition buildWeatherCondition() {
        return WeatherCondition.builder()
                .mainContent(new MainContent(BigDecimal.ONE, BigDecimal.TEN))
                .weather(List.of(new ExternalWeather("Rainy")))
                .build();
    }
}