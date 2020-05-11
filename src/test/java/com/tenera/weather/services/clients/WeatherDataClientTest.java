package com.tenera.weather.services.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.tenera.weather.exceptions.ExternalServiceException;
import com.tenera.weather.services.clients.models.ExternalWeather;
import com.tenera.weather.services.clients.models.MainContent;
import com.tenera.weather.services.clients.models.WeatherCondition;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
@AutoConfigureWireMock(port = 8765)
class WeatherDataClientTest {

    private WeatherDataClient urlForCityWeather;

    @BeforeEach
    void setUp() {
        urlForCityWeather = new WeatherDataClient("http://localhost:8765",
                "/data/2.5/weather",
                "app-id");
    }

    @Test
    void shouldGetTheLatestWeatherConditionsFromOpenWeatherForACity() throws IOException {
        stubFor(WireMock.get("/data/2.5/weather?q=Berlin&appid=app-id&units=metric")
                .withQueryParam("q", new EqualToPattern("Berlin"))
                .withQueryParam("appid", new EqualToPattern("app-id"))
                .withQueryParam("units", new EqualToPattern("metric"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(this.getClass().getClassLoader().getResourceAsStream("response.json").readAllBytes())
                ));

        WeatherCondition actualWeatherCondition = urlForCityWeather.getWeatherCondition("Berlin");
        assertThat(actualWeatherCondition).isEqualTo(buildWeatherCondition());
    }

    @Test
    void shouldThrowTheExternalServiceExceptionWhenHttp5xx() {
        stubFor(WireMock.get("/data/2.5/weather?q=Berlin&appid=app-id&units=metric")
                .withQueryParam("q", new EqualToPattern("Berlin"))
                .withQueryParam("appid", new EqualToPattern("app-id"))
                .withQueryParam("units", new EqualToPattern("metric"))
                .willReturn(aResponse()
                        .withStatus(503)
                ));

        Assertions.assertThrows(ExternalServiceException.class, () -> urlForCityWeather.getWeatherCondition("Berlin"));
    }

    private WeatherCondition buildWeatherCondition() {
        return WeatherCondition.builder()
                .mainContent(new MainContent(BigDecimal.ONE, BigDecimal.TEN))
                .weather(List.of(new ExternalWeather("Clear")))
                .build();
    }

}