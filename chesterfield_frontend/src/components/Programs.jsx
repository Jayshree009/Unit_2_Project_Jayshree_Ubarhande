// src/components/Programs.jsx
import React, { useEffect, useState } from "react";

const API_BASE =
  import.meta.env?.VITE_API_BASE || "http://localhost:8081/api";

async function http(method, path, body) {
  const opts = {
    method,
    headers: { "Content-Type": "application/json" },
  };
  if (body !== undefined) opts.body = JSON.stringify(body);

  const res = await fetch(`${API_BASE}${path}`, opts);

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(`${method} ${path} failed: ${res.status} ${text}`);
  }
  return res.status === 204 ? null : res.json();
}

// API helpers
function listPrograms() {
  return http("GET", "/programs");
}
function createProgram(data) {
  return http("POST", "/programs", data);
}
function updateProgram(id, data) {
  return http("PUT", `/programs/${id}`, data);
}
function deleteProgram(id) {
  return http("DELETE", `/programs/${id}`);
}


const emptyForm = {
  name: "",
  ageGroup: "",
  description: "",
};

export default function Programs() {
  const [programs, setPrograms] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadPrograms();
  }, []);

  async function loadPrograms() {
    setLoading(true);
    setError("");
    try {
      const data = await listPrograms();
      setPrograms(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error(err);
      setError("Failed to load programs.");
    } finally {
      setLoading(false);
    }
  }

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  function startEdit(program) {
    setEditingId(program.id);
    setForm({
      name: program.name || "",
      ageGroup: program.ageGroup || "",
      description: program.description || "",
    });
  }

  function cancelEdit() {
    setEditingId(null);
    setForm(emptyForm);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setSaving(true);
    setError("");

    // backend receives these fields
    const payload = {
      name: form.name.trim(),
      ageGroup: form.ageGroup.trim(),
      description: form.description.trim(),
    };

    try {
      if (editingId == null) {
        const created = await createProgram(payload);
        setPrograms((prev) => [...prev, created]);
      } else {
        const updated = await updateProgram(editingId, payload);
        setPrograms((prev) =>
          prev.map((p) => (p.id === editingId ? updated : p))
        );
      }
      cancelEdit();
    } catch (err) {
      console.error(err);
      setError("Failed to save program.");
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id) {
    const ok = window.confirm("Delete this program?");
    if (!ok) return;

    try {
      await deleteProgram(id);
      setPrograms((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      console.error(err);
      setError("Delete failed.");
    }
  }

  return (
    <div style={{ padding: "1.5rem" }}>
      <h2>Our Programs</h2>

      {error && (
        <div
          style={{
            margin: "1rem 0",
            padding: "0.75rem",
            background: "#ffe6e6",
            border: "1px solid #cc0000",
            color: "#660000",
          }}
        >
          {error}
        </div>
      )}

      {/* List */}
      <section style={{ marginBottom: "2rem" }}>


        {programs.length === 0 ? (
          <p>No programs yet.</p>
        ) : (
          <table
            style={{
              width: "100%",
              borderCollapse: "collapse",
              marginTop: "1rem",
            }}
          >
            <thead>
              <tr>
                <th style={thStyle}>Name</th>
                <th style={thStyle}>Age Group</th>
                <th style={thStyle}>Description</th>
                <th style={thStyle}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {programs.map((p) => (
                <tr key={p.id}>
                  <td style={tdStyle}>{p.name}</td>
                  <td style={tdStyle}>{p.ageGroup}</td>
                  <td style={tdStyle}>{p.description}</td>
                  <td style={tdStyle}>
                    <button onClick={() => startEdit(p)}>Edit</button>{" "}
                    <button onClick={() => handleDelete(p.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>

      {/* Form */}
      <section>
        <h3>{editingId ? "Edit Program" : "Add New Program"}</h3>

        <form
          onSubmit={handleSubmit}
          style={{ display: "flex", flexDirection: "column", gap: "0.75rem" }}
        >
          <label>
            Name:
            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleChange}
              required
              style={inputStyle}
            />
          </label>

          <label>
            Age Group:
            <input
              type="text"
              name="ageGroup"
              value={form.ageGroup}
              onChange={handleChange}
              style={inputStyle}
            />
          </label>

          <label>
            Description:
            <textarea
              name="description"
              value={form.description}
              onChange={handleChange}
              rows={3}
              style={{ ...inputStyle, resize: "vertical" }}
            />
          </label>

          <div style={{ display: "flex", gap: "1rem", marginTop: "1rem" }}>
            <button type="submit" disabled={saving}>
              {saving ? "Saving..." : editingId ? "Update Program" : "Create Program"}
            </button>
            {editingId && <button onClick={cancelEdit}>Cancel</button>}
          </div>
        </form>
      </section>
    </div>
  );
}

const thStyle = {
  borderBottom: "1px solid #ccc",
  textAlign: "left",
  padding: "0.5rem",
};

const tdStyle = {
  borderBottom: "1px solid #eee",
  textAlign: "left",
  padding: "0.5rem",
};

const inputStyle = {
  width: "100%",
  padding: "0.4rem",
  marginTop: "0.25rem",
  boxSizing: "border-box",
};
