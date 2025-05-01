# 📁 File Management System – Java Desktop App

![Platform](https://img.shields.io/badge/Platform-Windows-blue)
![Language](https://img.shields.io/badge/Language-Java-orange)
![UI](https://img.shields.io/badge/UI%20&%20UX-Desktop%20Application-brightgreen)
![Database](https://img.shields.io/badge/Database-MySQL%20(XAMPP)-red)

## 📌 Overview

The **File Management System** is a robust Java-based desktop application that allows users to manage files seamlessly. It offers a user-friendly graphical interface, reliable file operations (upload, delete, modify), powerful search & sort functions, and full backend support via MySQL (XAMPP). The application can be launched easily using a single `execute.bat` file.

This project is designed for both **end users** who want a simple file organizer and **developers** who want to extend or integrate this system into larger Java applications.

---

## ✨ Key Features

- 📤 **Upload Files** – Add files with metadata to the system  
- 🗑️ **Delete Files** – Securely remove files  
- ✏️ **Modify Files** – Rename or update file records  
- 🔍 **Search Files** – Quickly search files by name, date, or type  
- ↕️ **Sort Files** – Sort by size, date modified, name, etc.  
- 🗂️ **Database Integration** – Stores all metadata using MySQL (XAMPP)  
- 🖥️ **Easy Execution** – Just run `execute.bat` to start UI and DB  

---

## 🖼️ User Interface (UI)

The application features a **modern, clean, and responsive GUI** built with Java Swing.  
It offers intuitive menus, buttons, and panels for easy navigation.  
Consistent theming ensures a professional user experience.

---

## 🚀 How to Use (End Users)

> Follow these steps to run and use the application:

1. ✅ **Install XAMPP** and start **Apache + MySQL**
2. ✅ Import the SQL database (provided in `/database/` folder) into **phpMyAdmin**
3. ✅ Double-click `execute.bat` to launch the app (this starts both the UI and DB setup)
4. ✅ Use the GUI to:
   - Upload new files
   - Search for existing files
   - Modify file details
   - Delete unwanted files
5. ✅ Sorted files display based on your selected criteria

> No coding required! Everything is pre-configured for smooth use.

---

## 🛠️ How to Use (Developers)

> Want to improve or integrate this project into your workflow?

1. 🧩 **Clone this repository**
   ```bash
   git clone https://github.com/aayush61203/FileManagementSystem.git
   ```
2. 🧱 **Open the project in any Java IDE** (e.g., IntelliJ IDEA, Eclipse)
3. ⚙️ Ensure JDK 11+ is installed and MySQL is running via XAMPP
4. 🗃️ Import the provided database SQL file
5. 🧪 Explore these main modules:
   - `FileManager.java` – Core logic and operations
   - `UIManager.java` – Handles GUI components
   - `DBConnector.java` – Manages DB connection and queries
   - `execute.bat` – Batch script for automated launch
6. 🧑‍💻 Modify or extend features as needed

---

## ⚡ Tech Stack

- **Language:** Java  
- **Database:** MySQL (XAMPP)  
- **Interface:** Java Swing GUI  
- **Execution:** Batch Script (.bat)  
- **OS:** Windows (Recommended)


---

## 📬 Contact

📧 **Email:** [contactaayushshah@gmail.com](mailto:contactaayushshah@gmail.com)
🌐 **GitHub:** [@aayush61203](https://github.com/aayush61203)

Feel free to reach out if you have any questions or need assistance!
