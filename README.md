# Flight Search ✈️

Flight Search is an Android app, built with Kotlin and Jetpack Compose, that lets you search for an airport and browse every possible flight route from it to other airports in a bundled database — with the ability to save routes as favorites for quick access later.

## Features

- **Airport autocomplete search** — type an airport name or IATA code and get live suggestions as you type, debounced for smooth typing.
- **Browse routes from an airport** — once an airport is selected, see a full list of possible destination flights, ranked by destination airport passenger volume.
- **Favorite routes** — tap the star on any route to save it as a favorite; favorites are persisted and shown even without an active search.
- **Offline, pre-seeded database** — airport data ships with the app as a pre-built SQLite database, so search works instantly with no network calls.
- **Reactive UI** — all state (search text, suggestions, selected airport, routes, favorites) is combined into a single `StateFlow` that drives the Compose UI.

## Tech stack

- **Kotlin**
- **Jetpack Compose** (Material 3) for the UI
- **Room** (`androidx.room3`) for local persistence, including a pre-populated database loaded from assets via `createFromAsset`
- **KSP** for Room's annotation processing
- **Kotlin Coroutines / Flow** (`combine`, `flatMapLatest`, `debounce`) for reactive, debounced search and combined UI state
- **ViewModel** (`androidx.lifecycle:lifecycle-viewmodel-compose`) for UI state management
- **Navigation Compose** (`androidx.navigation:navigation-compose`)

## Project structure

```
app/src/main/java/com/example/flightsearch/
├── MainActivity.kt                     # App entry point
├── FlightSearchApplication.kt          # Application class / DI container setup
├── data/
│   ├── AppDatabase.kt                  # Room database, seeded from a bundled SQLite asset
│   ├── AirportEntity.kt / AirportDao.kt        # Airport table + queries (search, list all)
│   ├── FavoriteEntity.kt / FavoriteDao.kt      # Favorite routes table + queries
│   ├── FavoriteRoute.kt                # Room relation joining a favorite to its two airports
│   ├── RoomAirportRepository.kt        # AirportRepository implementation backed by Room
│   └── RoomFavoriteRepository.kt       # FavoriteRepository implementation backed by Room
├── domain/
│   ├── model/
│   │   ├── Airport.kt                  # Domain model for an airport
│   │   └── Flight.kt                   # Domain model for a departure → destination route
│   └── repository/
│       ├── AirportRepository.kt        # Repository interface for airport search/listing
│       └── FavoriteRepository.kt       # Repository interface for favorite routes
├── ui/
│   ├── AppViewModelProvider.kt         # ViewModel factory wiring
│   └── screens/
│       ├── HomeScreen.kt               # Main search screen composable
│       ├── AirportSuggestions.kt       # Autocomplete suggestion list
│       ├── FlightList.kt               # List of routes/favorites with favorite toggle
│       ├── HomeScreenUiState.kt        # Combined UI state data class
│       └── HomeScreenViewModel.kt      # Combines search, suggestions, selection, and favorites into UI state
├── util/
│   └── StateFlowUtil.kt                # Flow → StateFlow helper for list-shaped state
└── ui/theme/                           # Compose theme (color, typography)
```

## Getting started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest stable release)
- JDK 11+
- An Android device or emulator running **API 24 (Android 7.0)** or higher

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/SamratVsn/Flight-Search.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Run the app on an emulator or physical device.

Alternatively, build from the command line:

```bash
./gradlew assembleDebug
```

## How it works

1. On first launch, Room creates its database from a pre-built SQLite file bundled in the app's assets (`FlightSearch.db`), so airport data is available immediately with no setup step.
2. As the user types in the search box, `HomeScreenViewModel` debounces the input and queries `AirportRepository` for matching airports by name or IATA code.
3. Selecting an airport from the suggestions computes every possible route from that airport to all other airports in the database, sorted by destination passenger volume.
4. Tapping the star on a route calls `toggleFavorite()`, which inserts or removes a row in the `favorite` table via `FavoriteRepository`; favorited routes are shown even when no search is active.
5. All of this — search text, suggestions, selected airport, computed routes, and favorites — is combined into a single `HomeScreenUiState` that the Compose UI observes and renders.

## Contributing

Contributions, issues, and feature requests are welcome. Feel free to open a pull request or file an issue.

## License

No license has been specified for this project yet. If you plan to reuse this code, please check with the repository owner.
