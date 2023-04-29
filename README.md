# Movies App - README

## Description

The Movies App is an Android application developed in Kotlin that allows users to search for their favorite movies, view their detailed information, and add them to their favorites. The app follows the MVVM architecture pattern and uses several widely-used Jetpack libraries, including Retrofit, Room Database, Dependency Injection with Hilt, Data Binding, Navigation, Glide, and Paging 3.

## Features

The Movies App comes with the following features:

- Search for movies: Users can search for movies by their title.
- View movie details: Users can view detailed information about a movie, such as its title, poster, release date, rating, and a brief overview.
- Add to favorites: Users can add a movie to their favorites list for easy access later.
- Remove from favorites: Users can remove a movie from their favorites list if they no longer wish to keep it.
- Paging: The app uses Paging 3 to paginate search results, making it easier to load large amounts of data in a performant manner.
- Data binding: The app uses data binding to minimize boilerplate code and improve code readability.
- Dependency injection with Hilt: The app uses Hilt for dependency injection, making it easier to manage dependencies and improve code modularity.
- Room Database: The app uses Room Database to store user favorites in a local database, making it easier to access the data without requiring an internet connection.
- Retrofit: The app uses Retrofit to handle network requests and simplify the process of fetching data from the internet.

## Usage

To use the Movies App, follow these steps:

1. Open the app.
2. Enter a movie title in the search bar and press search button.
3. Wait for the search results to load.
4. Click on a movie to view its details.
5. To add a movie to your favorites, click on the heart icon in the movie details screen.
6. To view your favorites, click on the floating action button with a heart shape on it.


## Demonstration

https://user-images.githubusercontent.com/55719203/235309138-41487e5b-6ec2-4c8b-a0cb-45696bec58a1.mp4

## Installation

To install the Movies App, follow these steps:

1. Clone the repository to your local machine using `git clone https://github.com/alitrk/MovieApp.git`.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Credits

The Movies App was developed by Ali Türk and was built using the following technologies:

- Kotlin
- Android Studio
- MVVM Architecture
- Retrofit
- Room Database
- Hilt
- Data Binding
- Navigation
- Glide
- Paging 3
