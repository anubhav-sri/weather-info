package com.tenera.weather.services;

import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.services.clients.WeatherDataClient;

import static com.tenera.weather.mappers.WeatherDataMapper.mapWeatherConditionToWeatherInfo;

public class WeatherService {
    private WeatherDataClient weatherDataClient;

    public WeatherService(WeatherDataClient weatherDataClient) {
        this.weatherDataClient = weatherDataClient;
    }

    public WeatherInfo getWeatherInformationForCity(String cityName) {
        return mapWeatherConditionToWeatherInfo(weatherDataClient.getWeatherCondition(cityName));
    }
}
