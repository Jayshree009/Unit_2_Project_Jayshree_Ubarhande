// src/pages/TeachersPage.tsx (similar for ProgramsPage, ReviewsPage)
import { useEffect, useState } from "react";
import { api } from "../lib/api";
import { Teacher } from "../types";

export default function TeachersPage() {
  const [list, setList] = useState<Teacher[]>([]);
  useEffect(() => { api.listTeachers().then(setList); }, []);
  return (
    <main className="container">
      <h1>Our Teachers</h1>
      <ul>{list.map(t => <li key={t.id}><b>{t.name}</b> — {t.bio}</li>)}</ul>
    </main>
  );
}
