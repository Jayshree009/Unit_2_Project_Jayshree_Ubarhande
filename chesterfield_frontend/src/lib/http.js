const BASE = import.meta.env.VITE_API_BASE || '';

async function handle(res) {
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`HTTP ${res.status}: ${text}`);
  }
  // empty response bodies are OK
  try { return await res.json(); } catch { return null; }
}

export async function get(path) {
  const res = await fetch(BASE + path, { headers: { 'Accept': 'application/json' } });
  return handle(res);
}

export async function post(path, data) {
  const res = await fetch(BASE + path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
    body: JSON.stringify(data)
  });
  return handle(res);
}

export async function put(path, data) {
  const res = await fetch(BASE + path, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
    body: JSON.stringify(data)
  });
  return handle(res);
}

export async function del(path) {
  const res = await fetch(BASE + path, { method: 'DELETE' });
  return handle(res);
}
