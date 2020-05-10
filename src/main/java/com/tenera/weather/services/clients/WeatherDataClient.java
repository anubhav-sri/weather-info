package com.tenera.weather.services.clients;

import com.tenera.weather.exceptions.ExternalServiceException;
import com.tenera.weather.services.clients.models.WeatherCondition;
import org.springframework.web.reactive.function.client.WebClient;

public class WeatherDataClient {
    private static final String APP_ID_KEY = "appid";
    private static final String QUERY_KEY = "q";
    private final WebClient webClient;
    private String weatherConditionEndPoint;
    private String apiKey;

    public WeatherDataClient(String hostName, String weatherConditionEndPoint, String apiKey) {
        this.webClient = WebClient.create(hostName);
        this.weatherConditionEndPoint = weatherConditionEndPoint;
        this.apiKey = apiKey;
    }

    public WeatherCondition getWeatherCondition(String cityName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(weatherConditionEndPoint)
                        .queryParam(QUERY_KEY, cityName)
                        .queryParam(APP_ID_KEY, apiKey)
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
