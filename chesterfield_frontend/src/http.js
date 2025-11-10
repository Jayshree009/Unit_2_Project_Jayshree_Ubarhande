// src/http.js
const BASE = import.meta.env.VITE_API_BASE ?? "http://localhost:8081";

async function asJson(res) {
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`${res.status} ${res.statusText} — ${text}`);
  }
  // Some endpoints may return no content (204)
  if (res.status === 204) return null;
  return res.json();
}

export async function getJson(path) {
  const res = await fetch(`${BASE}${path}`);
  return asJson(res);
}

export async function postJson(path, body) {
  const res = await fetch(`${BASE}${path}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });
  return asJson(res);
}

export async function putJson(path, body) {
  const res = await fetch(`${BASE}${path}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });
  return asJson(res);
}

export async function del(path) {
  const res = await fetch(`${BASE}${path}`, { method: "DELETE" });
  return asJson(res);
}
