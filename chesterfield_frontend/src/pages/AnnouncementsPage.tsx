import { useEffect, useState } from "react";
import { api } from "../lib/api";
import { Announcement } from "../types";

export default function AnnouncementsPage() {
  const [items, setItems] = useState<Announcement[]>([]);
  const [title, setTitle] = useState("");
  const [body, setBody]   = useState("");

  useEffect(() => { api.listAnnouncements().then(setItems); }, []);

  async function add() {
    if (!title.trim() || !body.trim()) return;
    const created = await api.createAnnouncement({ title, body });
    setItems(prev => [created, ...prev]);
    setTitle(""); setBody("");
  }

  async function remove(id: number) {
    await api.deleteAnnouncement(id);
    setItems(prev => prev.filter(a => a.id !== id));
  }

  return (
    <main className="container">
      <h1>Announcements</h1>

      <section style={{marginBottom:16}}>
        <input placeholder="Title" value={title} onChange={e=>setTitle(e.target.value)} />
        <textarea placeholder="Body" value={body} onChange={e=>setBody(e.target.value)} />
        <button onClick={add}>Post</button>
      </section>

      <ul>
        {items.map(a => (
          <li key={a.id} style={{marginBottom:12}}>
            <strong>{a.title}</strong> <em>({new Date(a.createdAt).toLocaleString()})</em>
            <p>{a.body}</p>
            <button onClick={() => remove(a.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </main>
  );
}
