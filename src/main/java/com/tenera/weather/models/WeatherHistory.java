package com.tenera.weather.models;

import com.google.common.collect.EvictingQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Queue;

@Getter
@EqualsAndHashCode
public class WeatherHistory {
    private String cityName;
    private String state;
    private Queue<WeatherInfo> weatherInfos;

    public WeatherHistory(String cityName, String state) {
        this.cityName = cityName;
        this.state = state;
        this.weatherInfos = EvictingQueue.create(5);
    }

    public void addInfo(WeatherInfo weatherInfo) {
        this.weatherInfos.add(weatherInfo);
    }

}
