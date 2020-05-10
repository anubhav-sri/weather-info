package com.tenera.weather.services;

import com.tenera.weather.models.WeatherHistory;
import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.repositories.WeatherDataRepository;
import com.tenera.weather.services.clients.WeatherDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.tenera.weather.mappers.WeatherDataMapper.mapWeatherConditionToWeatherInfo;

@Service
public class WeatherService {
    private WeatherDataClient weatherDataClient;
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    public WeatherService(WeatherDataClient weatherDataClient, WeatherDataRepository weatherDataRepository) {
        this.weatherDataClient = weatherDataClient;
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherInfo getWeatherInformationForLocation(String cityName) {
        WeatherInfo weatherInfo = mapWeatherConditionToWeatherInfo(weatherDataClient.getWeatherCondition(cityName));

        weatherDataRepository.findById(cityName)
                .ifPresentOrElse(wh -> {
                    wh.getWeatherInfos().add(weatherInfo);
                    weatherDataRepository.save(wh);
                }, () -> {
                    WeatherHistory newHistory = new WeatherHistory(cityName, "");
                    newHistory.addInfo(weatherInfo);
                    weatherDataRepository.save(newHistory);
                });

        return weatherInfo;
    }
}
