// src/pages/BookTourPage.tsx
import { useState } from "react";
import { api } from "../lib/api";

export default function BookTourPage() {
  const [form, setForm] = useState({ parentName:"", email:"", phone:"", preferredDate:"", message:"" });
  const [ok, setOk] = useState("");

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    const res = await api.createTour(form);
    setOk(`Thanks ${res.parentName}! We’ll reach out to confirm your tour.`);
    setForm({ parentName:"", email:"", phone:"", preferredDate:"", message:"" });
  };

  return (
    <main className="container">
      <h1>Book a Tour</h1>
      <form onSubmit={submit}>
        <input required placeholder="Parent name" value={form.parentName} onChange={e=>setForm({...form,parentName:e.target.value})}/>
        <input required type="email" placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/>
        <input placeholder="Phone" value={form.phone} onChange={e=>setForm({...form,phone:e.target.value})}/>
        <input required type="date" value={form.preferredDate} onChange={e=>setForm({...form,preferredDate:e.target.value})}/>
        <textarea placeholder="Message" value={form.message} onChange={e=>setForm({...form,message:e.target.value})}/>
        <button type="submit">Submit</button>
      </form>
      {ok && <p style={{marginTop:12,color:"green"}}>{ok}</p>}
    </main>
  );
}
