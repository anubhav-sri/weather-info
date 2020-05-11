package com.tenera.weather.services.clients;

import com.tenera.weather.exceptions.ExternalServiceException;
import com.tenera.weather.services.clients.models.WeatherCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WeatherDataClient {
    private static final String APP_ID_KEY = "appid";
    private static final String QUERY_KEY = "q";
    private static final String UNITS_KEY = "units";
    private static final String METRIC_UNIT = "metric";
    private final WebClient webClient;
    private String weatherConditionEndPoint;
    private String apiKey;

    public WeatherDataClient(@Value("${external-weather.hostName}") String hostName,
                             @Value("${external-weather.currentEndpoint}") String currentWeatherEndPoint,
                             @Value("${external-weather.apiKey}") String apiKey) {
        this.webClient = WebClient.create(hostName);
        this.weatherConditionEndPoint = currentWeatherEndPoint;
        this.apiKey = apiKey;
    }

    public WeatherCondition getWeatherCondition(String cityName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(weatherConditionEndPoint)
                        .queryParam(QUERY_KEY, cityName)
                        .queryParam(APP_ID_KEY, apiKey)
                        .queryParam(UNITS_KEY, METRIC_UNIT)
                        .build())
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        throw new ExternalServiceException(clientResponse.statusCode(), clientResponse.toString());
                    } else {
                        return clientResponse.bodyToMono(WeatherCondition.class);
                    }
                })
                .block();


    }
}
