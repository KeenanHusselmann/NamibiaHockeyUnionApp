# MAP711S Group Assignment Assignment
![Application Screenshot](/screenshot1.jpg)

 In this task, students are expected to develop a mobile application for the Namibia
 Hockey Union (https://namibiahockey.org) in groups of four(4) or five (5) students.
 The minimum functional requirements of the application are, but not limited to, the
 following:
* team registration;
* event entries;
* player registration & management;
* real-time information sharing.


# Namibia Hockey Union Mobile Application

This application aims to provide the following core functionalities:

* **Team Registration:** Allows teams to register their details and manage their information.
* **Event Entries:** Enables teams to register for events and view event details.
* **Player Registration & Management:** Facilitates player registration and team roster management.
* **Real-Time Information Sharing:** Provides real-time updates, news, and notifications.

## Features

* User Authentication (Login/Signup) using Firebase Authentication.
* Team registration and management.
* Event registration and scheduling.
* Player registration and management.
* Real-time updates and notifications.
* User-friendly interface using Jetpack Compose.
* Data storage and retrieval using Firebase Firestore/Realtime Database.

## Technology Stack

* **Android Development:** Kotlin
* **UI Framework:** Jetpack Compose
* **Authentication:** Firebase Authentication
* **Database:** Firebase Firestore/Realtime Database
* **Navigation:** Jetpack Navigation Compose
* **Architecture:** MVVM (Model-View-ViewModel)

## Getting Started

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/KeenanHusselmann/MAP_711s_Project_Namibia_Hockey_App/tree/main
    ```

2.  **Open the project in Android Studio.**

3.  **Set up Firebase:**

    * Create a Firebase project on the Firebase console.
    * Add your Android app to the Firebase project.
    * Download the `google-services.json` file and place it in the `app/` directory.

4.  **Build and run the application on an emulator or physical device.**

## Dependencies

* Firebase Authentication
* Firebase Firestore/Realtime Database
* Jetpack Compose Navigation
* Jetpack Compose Lifecycle
* Other Android Jetpack libraries

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

# ├── pages/ # Player, Team, News Pages
# ├── screens/ # Login, Signup, Home, Registration Screens
# ├── navigation/ # App Navigation Setup
# ├── viewmodel/ # ViewModel logic
# ├── models/ # Data Models (Player, Team)
# ├── services/ # Firebase Service Layer
# └── utils/ # Utility Classes & Constants

## ✅ MoSCoW Prioritization

- Must Have:
  - Authentication
  - Team and Player Registration
  - Real-time News
- Should Have:
  - Event Participation
  - Role-based Access
- Could Have:
  - Push Notifications
  - Calendar View
- Won’t Have (in MVP):
  - Social Media Integration
  - E-Commerce Features

## 🧪 Testing

- ✅ Manual Testing on real Android devices
- ✅ Firebase Emulator Suite for safe DB operations
- ✅ Unit Testing (JUnit) on ViewModels and logic
- ✅ Peer Code Reviews

## 🔍 Screenshots

> Add screenshots or screen recordings of the app here.

## 🙌 Contributors

- Keenan Husselmann - Lead developer
- Lance Cloete - Team Lead
- Edmund Jansen - UI Developer
- Sander Santana - Backend developer



## 🌐 Website Link

Visit NHU Official Website: https://namibiahockey.org
