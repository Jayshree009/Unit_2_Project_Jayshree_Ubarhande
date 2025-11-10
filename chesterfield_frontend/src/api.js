// src/api.js

// ---- Base URL (frontend .env can override this) ----
const API_BASE = import.meta.env?.VITE_API_BASE || "http://localhost:8081/api";

// ---- Tiny fetch helpers ----
async function http(method, path, body) {
  const opts = { method, headers: { "Content-Type": "application/json" } };
  if (body !== undefined) opts.body = JSON.stringify(body);

  const res = await fetch(`${API_BASE}${path}`, opts);
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(`${method} ${path} failed: ${res.status} ${text}`);
  }
  return res.status === 204 ? null : res.json();
}

const get = (path) => http("GET", path);
const post = (path, body) => http("POST", path, body);
const put = (path, body) => http("PUT", path, body);
const del = (path) => http("DELETE", path);

// ---- Announcements ----
async function listAnnouncements() {
  return get("/announcements");
}
async function createAnnouncement(data) {
  // { title, message }
  return post("/announcements", data);
}
async function deleteAnnouncement(id) {
  return del(`/announcements/${id}`);
}

// ---- Programs ----
async function listPrograms() {
  return get("/programs");
}
async function getProgram(id) {
  return get(`/programs/${id}`);
}
async function createProgram(data) {
  return post("/programs", data);
}
async function updateProgram(id, data) {
  return put(`/programs/${id}`, data);
}
async function deleteProgram(id) {
  return del(`/programs/${id}`);
}

// ---- Tours ----
async function createTour(data) {
  // { parentName, parentEmail, childName, preferredDate, notes }
  return post("/tours", data);
}
async function listTours() {
  return get("/tours");
}

// ---- Weather ----
async function getWeather(city) {
  // e.g. "Chesterfield, MO"
  const enc = encodeURIComponent(city);
  return get(`/weather?city=${enc}`);
}

// ---- Exports ----
export {
  // named exports
  listAnnouncements,
  createAnnouncement,
  deleteAnnouncement,
  listPrograms,
  getProgram,
  createProgram,
  updateProgram,
  deleteProgram,
  createTour,
  listTours,
  getWeather,
};

// default export (optional import style)
const api = {
  listAnnouncements,
  createAnnouncement,
  deleteAnnouncement,
  listPrograms,
  getProgram,
  createProgram,
  updateProgram,
  deleteProgram,
  createTour,
  listTours,
  getWeather,
};
export default api;
