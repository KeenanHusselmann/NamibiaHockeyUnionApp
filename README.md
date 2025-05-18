# Namibia Hockey Union Mobile Application

This is a mobile application developed for the Namibia Hockey Union (NHU) to facilitate team registration, player management, event entries, and real-time news and updates. The app was developed as part of a student project with a focus on real-world software engineering principles using Android Studio, Jetpack Compose, and Firebase.

## 📱 Features

- 🔐 User Authentication (Sign Up, Login, Logout)
- 🏑 Team Registration & Management
- 👤 Player Registration & Categorization
- 📰 Real-Time News and Information Sharing

## 🚀 Tech Stack

- Android Studio (IDE)
- Kotlin & Jetpack Compose (UI Framework)
- Firebase Authentication & Firestore
- MVVM Architecture
- Jetpack Navigation Component

## 🧭 App Navigation

The app uses Jetpack Navigation Compose with the following navigation graph:

- `auth` → AuthScreen
- `login` → LoginScreen
- `signup` → SignUpScreen
- `teamSignup` → TeamRegistrationScreen
- `home` → HomeScreen
- `teams` → TeamsPage
- `news-headlines/{categoryId}` → NewsHeadlinesPage
- `news-details/{headlineId}` → NewsDetailsPage
- `/{playerCategoryId}` → PlayersCategoryPage
- `player-details/{playerId}` → PlayerDetailsPage

Navigation is controlled by a global NavController through a singleton object.

## 📁 Project Structure

