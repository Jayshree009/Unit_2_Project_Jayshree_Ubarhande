package com.example.academy.weather;

public class WeatherDTO {
    private String city;
    private Double tempC;        // make sure this is a number
    private String description;

    public WeatherDTO() {}

    public WeatherDTO(String city, Double tempC, String description) {
        this.city = city;
        this.tempC = tempC;
        this.description = description;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getTempC() { return tempC; }
    public void setTempC(Double tempC) { this.tempC = tempC; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
