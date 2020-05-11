package com.tenera.weather.services;

import com.tenera.weather.models.WeatherHistory;
import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.models.WeatherInfoHistoryDetails;
import com.tenera.weather.repositories.WeatherDataRepository;
import com.tenera.weather.services.clients.WeatherDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public WeatherInfo getWeatherInformationForLocation(String location) {
        WeatherInfo weatherInfo = mapWeatherConditionToWeatherInfo(weatherDataClient.getWeatherCondition(location));
        String cityName = location.split(",")[0];
        weatherDataRepository.findById(cityName)
                .ifPresentOrElse(wh -> {
                    wh.getWeatherInfos().add(weatherInfo);
                    weatherDataRepository.save(wh);
                }, () -> {
                    WeatherHistory newHistory = new WeatherHistory(cityName);
                    newHistory.addInfo(weatherInfo);
                    weatherDataRepository.save(newHistory);
                });

        return weatherInfo;
    }


    public WeatherInfoHistoryDetails getWeatherInfoHistory(String location) {

        return weatherDataRepository.findById(location).map(weatherHistory -> {
            ArrayList<WeatherInfo> weatherInfos = new ArrayList<>(weatherHistory.getWeatherInfos());
            return new WeatherInfoHistoryDetails(calculateAverageTemp(weatherInfos),
                    calculateAveragePressure(weatherInfos),
                    weatherInfos);
        }).orElse(new WeatherInfoHistoryDetails(BigDecimal.ZERO, BigDecimal.ZERO, List.of()));

    }

    private BigDecimal calculateAveragePressure(ArrayList<WeatherInfo> weatherInfos) {
        return BigDecimal.valueOf(weatherInfos.stream()
                .mapToDouble(wi -> wi.getPressure().doubleValue())
                .average()
                .orElse(0.0));
    }

    private BigDecimal calculateAverageTemp(ArrayList<WeatherInfo> weatherInfos) {
        return BigDecimal.valueOf(weatherInfos.stream()
                .mapToDouble(wi -> wi.getTemperature().doubleValue())
                .average()
                .orElse(0.0));
    }
}
