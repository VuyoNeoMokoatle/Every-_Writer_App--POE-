# ✍️ Every Writer App

**Developed by:** Neo Mokoatle & Khumbulani Mdlolo
**Language:** Kotlin
**Architecture:** MVVM + Repository Pattern
**Database:** Room (Offline) + Firebase (Cloud Sync)
**Version:** 1.0.0
**License:** MIT

---

## 📖 Overview

**Every Writer** is a powerful note-taking Android application designed to help users **create, edit, and organize notes** easily. The app supports **offline functionality using Room DB**, and synchronizes user data to the cloud when online via a **REST API** and **Firebase Authentication**.

This app aims to help writers, students, and professionals manage their written content anywhere, anytime — even without internet access.

---

## 🚀 Features

### 🔐 Authentication

* Secure user registration and login using Firebase Auth
* Password encryption with hashing
* Logout functionality

### 📝 Note Management

* Create, edit, and delete notes
* Categorize notes (Personal, Work, Ideas, etc.)
* Offline note saving (Room DB)
* Auto-sync when online

### ⚙️ Settings & Personalization

* Change user preferences
* Toggle light/dark theme
* Multi-language support (English + isiZulu/Sesotho)

### 🌐 REST API & Cloud Sync

* RESTful API used for synchronization
* Data hosted on Firebase Realtime Database
* Retrofit used for API communication

### 🔔 Push Notifications

* Firebase Cloud Messaging for real-time alerts (POE feature)

### 📴 Offline Mode with Sync

* Room DB stores data locally
* On reconnection, data syncs automatically

---

## 🧩 Tech Stack

| Category             | Tools/Frameworks               |
| -------------------- | ------------------------------ |
| Language             | Kotlin                         |
| Architecture         | MVVM                           |
| Database             | Room, Firebase                 |
| Network              | Retrofit                       |
| UI                   | Jetpack Compose / XML          |
| Version Control      | GitHub                         |
| CI/CD                | GitHub Actions                 |
| Testing              | JUnit, Espresso                |
| Dependency Injection | Hilt / Dagger (if implemented) |

---

## 🧪 Testing

* Unit tests written with **JUnit**
* UI tests using **Espresso**
* Continuous testing via **GitHub Actions**

---

## 🔄 GitHub Actions

Automated CI/CD workflow ensures that the app:

* Builds successfully on each commit
* Runs all tests automatically
* Prevents breaking builds from merging

```yaml
name: Android CI
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Gradle
        run: ./gradlew build
```

---

## 🧠 AI Tool Usage

AI tools (ChatGPT and GitHub Copilot) were used **ethically and transparently** during development for:

* Debugging and fixing syntax errors
* Generating UML diagrams
* Writing project documentation
* Clarifying architectural design patterns
  All generated material was **reviewed, verified, and edited** by the development team.

---

## 🧑‍💻 Developers

**Neo Mokoatle** — Lead Developer, UI/UX Designer
**Khumbulani Mdlolo** — Backend Integration & API Developer

---

## 🎥 Demo Video

Watch the app demonstration here:
(https://youtu.be/Wet4P4DjniA)

---

## 🏁 How to Run

1. Clone this repository:

   ```bash
   git clone https://github.com/NeoMokoatle/EveryWriterApp.git
   ```
2. Open the project in **Android Studio**
3. Sync Gradle and run the app on a physical Android device
4. Login/Register and start writing notes

---

## 📝 License

This project is licensed under the **MIT License** — feel free to use and modify it.

