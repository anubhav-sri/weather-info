package com.tenera.weather.controllers;

import com.tenera.weather.MongoDBIntegrationTests;
import com.tenera.weather.services.clients.WeatherDataClient;
import com.tenera.weather.services.clients.models.ExternalWeather;
import com.tenera.weather.services.clients.models.MainContent;
import com.tenera.weather.services.clients.models.WeatherCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherInfoControllerTest extends MongoDBIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherDataClient weatherDataClient;

    @Test
    void shouldRespondWith200StatusAndValidWeatherResponseForACity() throws Exception {
        when(weatherDataClient.getWeatherCondition("berlin")).thenReturn(buildWeatherCondition());
        mockMvc.perform(get("/current?location=berlin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temp").value("1"))
                .andExpect(jsonPath("$.pressure").value("10"))
                .andExpect(jsonPath("$.umbrella").value(true));


    }

    @Test
    void shouldRespondWith200StatusAndHistoreyBasedOnPreviousCalls() throws Exception {
        when(weatherDataClient.getWeatherCondition("berlin")).thenReturn(buildWeatherCondition());
        mockMvc.perform(get("/current?location=berlin"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/history?location=berlin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avg_temp").value(1.0))
                .andExpect(jsonPath("$.avg_pressure").value(10.0))
                .andExpect(jsonPath("$.history[0].temp").value(1.0))
                .andExpect(jsonPath("$.history[0].pressure").value(10.0))
                .andExpect(jsonPath("$.history[0].umbrella").value(true));


    }

    private WeatherCondition buildWeatherCondition() {
        return WeatherCondition.builder()
                .mainContent(new MainContent(BigDecimal.ONE, BigDecimal.TEN))
                .weather(List.of(new ExternalWeather("Rainy")))
                .build();
    }
}