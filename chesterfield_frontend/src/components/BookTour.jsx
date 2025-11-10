import { useState } from "react";
import api from "../api";

export default function BookTour() {
  const [form, setForm] = useState({
    parentName: "",
    parentEmail: "",
    childName: "",
    preferredDate: "",
    notes: ""
  });
  const [msg, setMsg] = useState("");

  const onChange = e => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const submit = async (e) => {
    e.preventDefault();
    setMsg("");
    try {
      await api.createTour(form);
      setMsg("Tour booked! A confirmation email has been sent.");
      setForm({ parentName:"", parentEmail:"", childName:"", preferredDate:"", notes:"" });
    } catch (err) {
      setMsg(`Booking failed: ${err.message}`);
    }
  };

  return (
    <div style={{maxWidth:520, margin:"24px auto"}}>
      <h2>Book a Tour</h2>
      <form onSubmit={submit} style={{display:"grid", gap:12}}>
        <input name="parentName" placeholder="Parent name" value={form.parentName} onChange={onChange} required />
        <input type="email" name="parentEmail" placeholder="Parent email" value={form.parentEmail} onChange={onChange} required />
        <input name="childName" placeholder="Child name" value={form.childName} onChange={onChange} required />
        <input type="date" name="preferredDate" value={form.preferredDate} onChange={onChange} required />
        <textarea name="notes" placeholder="Notes (optional)" value={form.notes} onChange={onChange} />
        <button type="submit" style={{padding:"8px 12px"}}>Submit</button>
      </form>
      {msg && <p style={{marginTop:12}}>{msg}</p>}
    </div>
  );
}
