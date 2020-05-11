package com.tenera.weather.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@EqualsAndHashCode
public class WeatherHistory {
    @Id
    @Indexed
    private String cityName;
    private String state;
    private LimitedSelfHealingListFor5Elements<WeatherInfo> weatherInfos;

    public WeatherHistory(String cityName, String state) {
        this.cityName = cityName;
        this.state = state;
        this.weatherInfos = new LimitedSelfHealingListFor5Elements<>();
    }

    public void addInfo(WeatherInfo weatherInfo) {
        this.weatherInfos.add(weatherInfo);
    }

}
