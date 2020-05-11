package com.tenera.weather.models;

import com.google.common.collect.EvictingQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Collection;
import java.util.Queue;

@Getter
@EqualsAndHashCode
public class WeatherHistory {
    @Id
    @Indexed
    private String cityName;
    private String state;
    private Collection<WeatherInfo> weatherInfos;

    public WeatherHistory(String cityName, String state) {
        this.cityName = cityName;
        this.state = state;
        this.weatherInfos = EvictingQueue.create(5);
    }

    public void addInfo(WeatherInfo weatherInfo) {
        this.weatherInfos.add(weatherInfo);
    }

}
