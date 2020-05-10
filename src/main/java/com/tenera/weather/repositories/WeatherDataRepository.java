package com.tenera.weather.repositories;

import com.tenera.weather.models.WeatherHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherDataRepository extends MongoRepository<WeatherHistory, String> {
}
