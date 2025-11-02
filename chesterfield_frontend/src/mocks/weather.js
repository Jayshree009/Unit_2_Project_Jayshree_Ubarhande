// src/mocks/weather.js
export function getMockWeather(city) {
  return {
    location: city || "Chesterfield, MO",
    tempF: 64,
    conditions: "Partly Cloudy",
    icon: "⛅",
    advice: "Light jacket + closed shoes.",
  };
}
