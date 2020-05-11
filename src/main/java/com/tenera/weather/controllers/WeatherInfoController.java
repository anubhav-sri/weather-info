package com.tenera.weather.controllers;

import com.tenera.weather.dtos.WeatherInfoDTO;
import com.tenera.weather.dtos.WeatherInfoHistoryDetailsDto;
import com.tenera.weather.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.tenera.weather.mappers.WeatherDataMapper.mapWeatherInfoDetailsToDTO;
import static com.tenera.weather.mappers.WeatherDataMapper.mapWeatherInfoToWeatherInfoDTO;

@RestController
public class WeatherInfoController {
    private WeatherService weatherService;

    @Autowired
    public WeatherInfoController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/current")
    public WeatherInfoDTO getCurrentWeather(@RequestParam("location") String location) {
        return mapWeatherInfoToWeatherInfoDTO(weatherService.getWeatherInformationForLocation(location));
    }

    @GetMapping(value = "/history")
    public WeatherInfoHistoryDetailsDto getHistory(@RequestParam("location") String location) {
        return mapWeatherInfoDetailsToDTO(weatherService.getWeatherInfoHistory(location));
    }
}
