import { get, post, put, del } from './http';
//import { http } from "./http";

import { getMockWeather } from "../mocks/weather";
//import { getMockWeather } from "../mocks/weather";



// import mocks
import { listAnnouncements as mockListAnnouncements } from '../mocks/announcements';
//import { listPrograms as mockListPrograms } from '../mocks/programs';
//import { listReviews as mockListReviews } from '../mocks/reviews';
//import { listTeachers as mockListTeachers } from '../mocks/teachers';
//import { getWeather as mockGetWeather } from '../mocks/weather';

const USE_MOCKS = String(import.meta.env.VITE_USE_MOCKS).toLowerCase() === 'true';
// Small delay so the UI feels like a real request
const delay = (data, ms = 300) =>
  new Promise((res) => setTimeout(() => res(data), ms));


// ---- Announcements ----
export async function listAnnouncements() {
  if (USE_MOCKS) return mockListAnnouncements();
  return get('/api/announcements');
}
export async function createAnnouncement(a) {
  if (USE_MOCKS) throw new Error('POST disabled in mock mode');
  return post('/api/announcements', a);
}
// Weather
export function getWeather(city) {
  if (USE_MOCKS) return delay(getMockWeather(city));
  const q = encodeURIComponent(city || "");
  return http.get(`/weather?city=${q}`).then((r) => r.data);
}

// ---------- WEATHER ----------
/*export async function getWeather(city: string) {
  if (USE_MOCKS) return delay(getMockWeather(city));
  // Example real API (you’ll build on Milestone 5)
  // return http.get(`/weather?city=${encodeURIComponent(city)}`).then(r => r.data);
  return http.get(`/weather?city=${encodeURIComponent(city)}`).then(r => r.data);
}*/


