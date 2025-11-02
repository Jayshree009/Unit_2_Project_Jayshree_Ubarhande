import { useEffect, useState } from "react";
import { getWeather } from "../lib/api";

export default function WeatherWidget({ city = "Chesterfield, MO" }) {
  const [weather, setWeather] = useState(null);

  useEffect(() => {
    getWeather(city).then(setWeather);
  }, [city]);

  if (!weather) return <div>Weather: Loading…</div>;

  return (
    <div
      style={{
        border: "1px solid #e5e7eb",
        borderRadius: 12,
        padding: 16,
        display: "flex",
        alignItems: "center",
        gap: 12,
      }}
    >
      <span style={{ fontSize: 28 }}>{weather.icon}</span>
      <div>
        <div style={{ fontWeight: 600 }}>
          {weather.location} — {weather.tempF}°F, {weather.conditions}
        </div>
        <div style={{ color: "#6b7280" }}>Suggestion: {weather.advice}</div>
      </div>
    </div>
  );
}
