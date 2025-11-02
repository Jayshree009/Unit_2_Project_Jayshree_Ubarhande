const data = [
  { id: 1, title: "School Closed Friday, yayyyyy", body: "We’re closed for maintenance.", publishedAt: "2025-10-10T09:00:00Z" },
  { id: 2, title: "Picture Day!", body: "Wear your favorite outfit on Monday.", publishedAt: "2025-10-12T09:00:00Z" }
];

export async function listAnnouncements() {
  return Promise.resolve(data);
}
