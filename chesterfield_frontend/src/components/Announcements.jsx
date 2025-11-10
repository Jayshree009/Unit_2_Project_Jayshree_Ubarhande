import { useEffect, useState } from "react";
import { listAnnouncements, createAnnouncement, deleteAnnouncement } from "../api";

export default function Announcements() {
  const [items, setItems] = useState([]);
  const [title, setTitle] = useState("");
  const [message, setMessage] = useState("");
  const [busy, setBusy] = useState(false);

  async function load() {
    try {
      const data = await listAnnouncements(); // expect [{id, title, message, postedAt}]
      setItems(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error("Announcements load failed:", e);
      setItems([]);
    }
  }

  useEffect(() => { load(); }, []);

  async function onCreate(e) {
    e.preventDefault();
    if (!title.trim() || !message.trim()) return;
    setBusy(true);
    try {
      await createAnnouncement({ title: title.trim(), message: message.trim() });
      setTitle(""); setMessage("");
      await load();
    } catch (e) {
      alert("Create failed: " + e.message);
    } finally {
      setBusy(false);
    }
  }

  async function onDelete(id) {
    if (id == null) {
      console.warn("Delete clicked but id is missing:", id);
      return;
    }
    try {
      await deleteAnnouncement(id);
      setItems(prev => prev.filter(a => a.id !== id));
    } catch (e) {
      alert("Delete failed: " + e.message);
    }
  }

  return (
    <section className="announcements" style={{maxWidth:720, margin:"24px auto"}}>
      <h2>Announcements</h2>

      <form onSubmit={onCreate} style={{display:"grid", gap:8, marginBottom:16}}>
        <input value={title} onChange={e=>setTitle(e.target.value)} placeholder="Title" />
        <textarea value={message} onChange={e=>setMessage(e.target.value)} placeholder="Message" rows={3}/>
        <button disabled={busy}>Post</button>
      </form>

      <ul style={{listStyle:"none", padding:0, display:"grid", gap:12}}>
        {items.map(a => (
          <li key={a.id ?? `${a.title}-${a.postedAt}`}>
            <div style={{border:"1px solid #eee", borderRadius:12, padding:12, boxShadow:"0 1px 4px rgba(0,0,0,.05)"}}>
              <div style={{display:"flex", justifyContent:"space-between", alignItems:"center"}}>
                <strong>{a.title}</strong>
                {a.id != null && (
                  <button onClick={() => onDelete(a.id)} style={{background:"#f44336", color:"#fff", border:"0", padding:"6px 10px", borderRadius:8, cursor:"pointer"}}>
                    Delete
                  </button>
                )}
              </div>
              <p style={{margin:"8px 0 0"}}>{a.message}</p>
              {a.postedAt && <div style={{fontSize:12, color:"#666", marginTop:6}}>{new Date(a.postedAt).toLocaleString()}</div>}
            </div>
          </li>
        ))}
      </ul>
    </section>
  );
}
