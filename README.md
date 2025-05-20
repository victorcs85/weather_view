# Project WeatherView

This project is a simple weather application that allows users to view the current weather conditions for a specified location. It uses the OpenWeatherMap API to fetch weather data and displays it in a user-friendly format.
Using AdMob SDK to show ads in the app to test your implementations.

## Technologies used 🤘

- Kotlin
- Android Studio
- OpenWeatherMap API ⛅
- AdMob SDK
- Ktor
- Coroutines
- Jetpack Compose
- ViewModel
- Navigation
- Kotlin Serialization
- Kotlin Flow

## Architecture 🚀

- MVI
- Clean Architecture 3 layers (Presentation, Domain, Data).
- Core package with common data.

## Features 🚥

- Display the current temperature of 2 predefined cities for testing (São Paulo and Amazonas).
- Display through concurrency with coroutines.
- Display of the AdMob Banner.

## Unit Tests 🔧

- Unit tests for the ViewModel and Repository.
- Used JUnit for testing and Mock.

## How to run the project 🏃

- Clone the repository.
- Open the project in Android Studio.
- Make sure you have the Android SDK installed.
- Run the project on an emulator or Android device.
- Make sure you have a valid OpenWeatherMap API key and add it to the `gradle.properties` file as `WEATHER_KEY`.
- Make sure you have a valid AdMob banner key and add it to the `gradle.properties` file as `BANNER_ID`.
