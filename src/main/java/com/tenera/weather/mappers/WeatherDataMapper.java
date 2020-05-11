package com.tenera.weather.mappers;

import com.tenera.weather.dtos.WeatherInfoDTO;
import com.tenera.weather.dtos.WeatherInfoHistoryDetailsDto;
import com.tenera.weather.models.WeatherInfo;
import com.tenera.weather.models.WeatherInfoHistoryDetails;
import com.tenera.weather.models.WeatherType;
import com.tenera.weather.services.clients.models.WeatherCondition;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherDataMapper {
    private static List<WeatherType> unbrellaWeather = List.of(WeatherType.THUNDERSTORM,
            WeatherType.DRIZZLE, WeatherType.RAINY);


    public static WeatherInfo mapWeatherConditionToWeatherInfo(WeatherCondition weatherCondition) {

        return WeatherInfo.builder()
                .temperature(weatherCondition.getMainContent().getTemperature())
                .pressure(weatherCondition.getMainContent().getPressure())
                .weatherType(WeatherType.valueOfWeather(weatherCondition.getWeather().get(0).getMain()))
                .build();
    }

    public static WeatherInfoDTO mapWeatherInfoToWeatherInfoDTO(WeatherInfo weatherInfo) {

        return WeatherInfoDTO.builder()
                .temp(weatherInfo.getTemperature())
                .pressure(weatherInfo.getPressure())
                .umbrella(deriveIsUmbrella(weatherInfo.getWeatherType()))
                .build();
    }

    public static WeatherInfoHistoryDetailsDto mapWeatherInfoDetailsToDTO(WeatherInfoHistoryDetails weatherDetails) {

        List<WeatherInfoDTO> weatherInfoDtoList = weatherDetails.getWeatherInfos().stream().map(WeatherDataMapper::mapWeatherInfoToWeatherInfoDTO).collect(Collectors.toList());
        return WeatherInfoHistoryDetailsDto.builder()
                .averagePressure(weatherDetails.getAveragePressure())
                .averageTemperature(weatherDetails.getAverageTemperature())
                .weatherInfos(weatherInfoDtoList)
                .build();
    }

    private static Boolean deriveIsUmbrella(WeatherType weatherType) {
        return unbrellaWeather.contains(weatherType);
    }
}
