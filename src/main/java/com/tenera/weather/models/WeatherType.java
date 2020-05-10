package com.tenera.weather.models;

public enum WeatherType {
    RAINY("Rainy"),
    CLEAR("Clear"),
    THUNDERSTORM("Thunderstorm"),
    DRIZZLE("Drizzle");

    private String value;

    WeatherType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static WeatherType valueOfWeather(String value){
        for (WeatherType e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;

    }
}
