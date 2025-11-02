import { useEffect, useState } from 'react';
import { listAnnouncements } from '../lib/api';

export default function Announcements() {
  const [items, setItems] = useState([]);
  const [err, setErr] = useState('');

  useEffect(() => {
    (async () => {
      try {
        const rows = await listAnnouncements();
        setItems(rows);
      } catch (e) {
        setErr(e.message || 'Failed to load announcements');
      }
    })();
  }, []);

  if (err) return <div className="text-red-600">{err}</div>;
  if (!items.length) return <div>Loading announcements…</div>;

  return (
    <section>
      <h2>Announcements</h2>
      <ul>
        {items.map(a => (
          <li key={a.id} style={{ marginBottom: 12 }}>
            <strong>{a.title}</strong>
            <div>{a.body}</div>
            <small>{new Date(a.publishedAt).toLocaleString()}</small>
          </li>
        ))}
      </ul>
    </section>
  );
}
