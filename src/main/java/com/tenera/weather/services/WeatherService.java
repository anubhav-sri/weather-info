package com.tenera.weather.services;

import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.services.clients.WeatherDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tenera.weather.mappers.WeatherDataMapper.mapWeatherConditionToWeatherInfo;

@Service
public class WeatherService {
    private WeatherDataClient weatherDataClient;

    @Autowired
    public WeatherService(WeatherDataClient weatherDataClient) {
        this.weatherDataClient = weatherDataClient;
    }

    public WeatherInfo getWeatherInformationForLocation(String cityName) {
        return mapWeatherConditionToWeatherInfo(weatherDataClient.getWeatherCondition(cityName));
    }
}
