package com.tenera.weather.mappers;

import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.models.WeatherType;
import com.tenera.weather.services.clients.models.WeatherCondition;

public class WeatherDataMapper {


    public static WeatherInfo mapWeatherConditionToWeatherInfo(WeatherCondition weatherCondition) {

        return WeatherInfo.builder()
                .temperature(weatherCondition.getMainContent().getTemperature())
                .pressure(weatherCondition.getMainContent().getPressure())
                .weatherType(WeatherType.valueOfWeather(weatherCondition.getWeather().get(0).getMain()))
                .build();
    }
}
