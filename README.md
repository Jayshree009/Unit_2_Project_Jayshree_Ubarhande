<img width="975" height="905" alt="image" src="https://github.com/user-attachments/assets/e2b268ea-6139-4e1b-b8a2-b3d2c090522e" />

# Chesterfield Academy: Full-Stack Preschool Management App

---

## 📌 Project Overview

Chesterfield Academy is a modern preschool/daycare website built as part of LaunchCode’s Full-Stack Web Development course.

This application simulates a real preschool website where parents can:

- View programs offered by the school  
- Read school announcements  
- Check today’s weather (via **OpenWeather API**)  
- Book a school tour (with automatic confirmation email via **SendGrid**)  

The backend handles:

- Weather API integration  
- Email delivery  
- Database CRUD operations for Programs, Announcements, and Tours  

This project demonstrates full front-to-back integration using React, Spring Boot, MySQL, REST APIs, and clean Git workflows.

---

## 🛠️ Technologies Used

### Frontend
- React  
- React Router  
- JSX & Components  
- Custom CSS  
- Fetch API (async calls)  
- Vite  

### Backend
- Java  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Hibernate ORM  
- MySQL Database  

### Tools / DevOps
- Git & GitHub  
- Postman (API testing)  
- Maven  
- VS Code  
- IntelliJ  

---

## ✨ Features

- Real-time weather widget using **OpenWeather API**  
- Programs listing page (stored in MySQL)  
- Announcements CRUD (Create / Read / Delete)  
- Book a Tour form with **SendGrid email integration**  
- Fully responsive UI  

---

## 📘 Database Schema (ER Diagram Description)
<img width="975" height="685" alt="image" src="https://github.com/user-attachments/assets/74627a41-3407-4c34-b4cb-f9f0323cdad3" />

## 📝Wireframing
<img width="714" height="744" alt="image" src="https://github.com/user-attachments/assets/d5ae401c-a6c1-466f-915f-a4023f740b16" />


---

## 📡 API Endpoints Summary

### Programs

| Method | Endpoint          | Description              |
|--------|-------------------|--------------------------|
| GET    | `/api/programs`   | Retrieve all programs    |
| POST   | `/api/programs`   | Create a new program     |

---

### Announcements

| Method | Endpoint                    | Description                  |
|--------|-----------------------------|------------------------------|
| GET    | `/api/announcements`        | Retrieve all announcements   |
| POST   | `/api/announcements`        | Create a new announcement    |
| DELETE | `/api/announcements/{id}`   | Delete an announcement       |

---

### Tours

| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| GET    | `/api/tours`       | Retrieve all tour bookings |
| POST   | `/api/tours`       | Create a new tour booking |
| DELETE | `/api/tours/{id}`  | Delete a tour booking     |

---

### Weather

| Method | Endpoint                                  | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | `/api/weather?city=Chesterfield`          | Fetch current weather for Chesterfield, MO       |

---

# 🚀 Installation Instructions

Follow these steps to run the full-stack app locally:

---

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/Jayshree009/Unit_2_Project_Jayshree_Ubarhande
cd Unit_2_Project_Jayshree_Ubarhande
```

## 2️⃣ **Backend Setup (Spring Boot)**
Create MYSQL Database
```sql
CREATE DATABASE chesterfield_academy;
```
Set API Keys (IMPORTANT)

PowerShell:
```bash
$env:SENDGRID_API_KEY="your-sendgrid-key"
$env:OPENWEATHER_API_KEY="your-weather-key"
```
Or create a local file named
```bash
application-local.properties
```
Do NOT commit API keys to GitHub.
**Start Backend**
```cd academy
./mvnw spring-boot:run
```
Backend runs at:

http://localhost:8081

## 3️⃣ **Frontkend Setup (React)**
```cd chesterfield_frontend
npm install
npm run dev
```
Frontend runs at:

http://localhost:5178
##  **Future Enhancements**
- UI for editing announcements
- Admin login system (authentication)
- UI for viewing booked tours
- Homepage animations
- Dynamic teacher profiles from database

 ## **👩‍💻Author**
 **Jayshree Ubarhande** 
 
Full-Stack Developer (React + Spring Boot) 

LaunchCode Full-Stack Web Development Program
