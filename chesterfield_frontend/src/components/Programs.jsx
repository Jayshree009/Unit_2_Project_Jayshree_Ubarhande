// chesterfield_frontend/src/components/Programs.jsx
import { useEffect, useState } from "react";
import { listPrograms } from "../api";
import "./Programs.css";

export default function Programs() {
  const [items, setItems] = useState([]);
  useEffect(() => {
    listPrograms().then(setItems).catch(() => setItems([]));
  }, []);
  return (
    <section>
      <h2>Our Programs</h2>
      <div className="programs-grid">
        {items.map(p => (
          <div key={p.id} className="program-card">
            <div className="program-title">{p.name}</div>
            <div className="program-age">{p.ageGroup}</div>
            <div className="program-desc">{p.description}</div>
          </div>
        ))}
      </div>
    </section>
  );
}
