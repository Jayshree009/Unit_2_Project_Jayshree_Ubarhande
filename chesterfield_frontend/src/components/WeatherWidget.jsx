import { useEffect, useState } from "react";
import { getWeather } from "../api";

export default function WeatherWidget() {
  const [state, setState] = useState({ loading: true, data: null, error: null });

  useEffect(() => {
    async function load() {
      try {
        const data = await getWeather("Chesterfield");
        setState({ loading: false, data, error: null });
      } catch (e) {
        setState({ loading: false, data: null, error: e.message });
      }
    }
    load();
  }, []);

  const tempText =
    state.data?.tempC != null && !Number.isNaN(state.data.tempC)
      ? Math.round(state.data.tempC) + "°"
      : "—°";

  return (
    <section style={{ padding: "2rem 0", textAlign: "center" }}>
      <h2 style={{ fontSize: "2rem", fontWeight: 700 }}>Today’s Weather</h2>
      <p style={{ marginTop: 8 }}>Chesterfield, MO: {tempText}</p>
      {state.data?.description && (
        <p style={{ color: "#555" }}>{state.data.description}</p>
      )}
      {state.error && <p style={{ color: "crimson" }}>Weather unavailable</p>}
    </section>
  );
}
