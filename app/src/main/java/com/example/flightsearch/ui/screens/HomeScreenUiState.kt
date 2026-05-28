package com.example.flightsearch.ui.screens

import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.model.Flight

data class HomeScreenUiState(
    val searchText: String = "",
    val airportSuggestions: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null,
    val routes: List<Flight> = emptyList(),
    val favoriteFlights: List<Flight> = emptyList(),
)