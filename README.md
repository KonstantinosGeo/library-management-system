# 📚 Library Management System

A comprehensive Java desktop application for library administration, enabling administrators to manage a books database through an intuitive graphical user interface. Built as an individual academic project for the **ECE318: Programming Principles for Engineers** course at the **University of Cyprus**.

---

## 🎯 Overview

This application allows library administrators to view, add, update, and delete books, genres, and sub-genres, with advanced features such as filtering, sorting, keyword search, and PDF report generation. The project demonstrates strong application of **Object-Oriented Programming (OOP)** principles, GUI development, and data management.

---

## ✨ Features

### 📖 Book Management
- **Full CRUD operations** — Create, Read, Update, Delete book records
- **Keyword search** — Find books by title or author
- **Multi-criteria filtering** — Filter by main genre and sub-genre, with multi-select support
- **Dynamic sorting** — Sort by rating or price (ascending/descending)
- **Detailed view** — Display all book attributes (genre, author, type, price, rating, etc.)

### 🏷️ Genre & Sub-Genre Management
- Full CRUD operations for both main genres and sub-genres
- Search by keyword within genre names
- **Statistical calculations:**
  - Total number of books per genre/sub-genre
  - Average rating per genre/sub-genre
  - Average price per genre/sub-genre

### 📄 PDF Report Generation
- Export full or filtered book lists to PDF
- Generate genre/sub-genre reports with computed statistics
- Powered by the **iText** library

### 🖥️ User Interface
- Multi-tab GUI built with **Java Swing**
- Separate management pages for Books, Genres, and Sub-genres
- Smooth navigation, input validation, and informative error messages

---

## 🛠️ Technologies Used

| Category | Technology |
|----------|-----------|
| **Language** | Java |
| **GUI Framework** | Java Swing |
| **PDF Library** | iText 5.5.13.3 |
| **Data Storage** | CSV files |
| **IDE** | IntelliJ IDEA |

---

## 🏗️ Architecture & OOP Principles

The application is designed following core OOP principles:

- **Encapsulation** — Private fields with controlled access through getters/setters
- **Abstraction** — Clean separation between data models, business logic, and UI
- **Inheritance** — Hierarchical class structure for genres and sub-genres
- **Polymorphism** — Flexible handling of related entity types

### 📊 Class Structure

| Class | Responsibility |
|-------|----------------|
| `Main` | Application entry point |
| `Book` | Book entity model |
| `Genre` | Main genre entity model |
| `SubGenre` | Sub-genre entity model |
| `LibraryManager` | Core business logic and data management |
| `LibraryGUI` | Main application window |
| `GenresPanel` | Genre management UI |
| `SubGenresPanel` | Sub-genre management UI |
| `BookDetailsWindow` | Book details view |
| `PDFExporter` | PDF report generation |

---

## 📂 Dataset

The application uses the **Amazon Books Dataset** from Kaggle, structured across three interconnected CSV files:

- `Books_df.csv` — Book records (title, author, genre, sub-genre, type, price, rating, etc.)
- `Genre_df.csv` — Main genres with metadata
- `Sub_Genre_df.csv` — Sub-genres linked to main genres

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- IntelliJ IDEA (or any Java IDE)
- iText library 5.5.13.3

### Running the Application
1. Clone this repository
2. Open the project in IntelliJ IDEA
3. Add the iText library to the project dependencies
4. Run `Main.java`

---

## 📑 Documentation

- 📋 **Project Specification:** [`318Project_2025_v1.pdf`](318Project_2025_v1.pdf)
- 📐 **UML Diagrams:** [`UML DIAGRAMS.pdf`](UML%20DIAGRAMS.pdf)

---

## 👨‍💻 Author

**Konstantinos Georgiou**
Electrical & Computer Engineering Student
University of Cyprus

---

## 📝 Academic Context

Developed as an individual coursework project for **ECE318: Programming Principles for Engineers** (2025), Department of Electrical and Computer Engineering, University of Cyprus.
