package com.example.flightsearch.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.model.Flight
import com.example.flightsearch.domain.repository.AirportRepository
import com.example.flightsearch.domain.repository.FavoriteRepository
import com.example.flightsearch.util.listStateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val searchTextFlow = MutableStateFlow("")

    private val selectedAirportFlow = MutableStateFlow<Airport?>(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val suggestionsFlow = searchTextFlow.debounce(300).flatMapLatest { searchText ->
        if (searchText.isEmpty()) flowOf(emptyList())
        else airportRepository.searchAirportsFlow("%$searchText%")
    }.listStateIn(viewModelScope)

    private val favoritesFlow = favoriteRepository.getFavoritesFlow().listStateIn(viewModelScope)

    private val airportsFlow = airportRepository.getAirportsFlow().listStateIn(viewModelScope)

    private val airportRoutesFlow = combine(
        selectedAirportFlow,
        airportsFlow,
        favoritesFlow,
    ) { selectedAirport, airports, favoriteFlights ->
        if (selectedAirport == null) emptyList<Flight>()
        else airports
            .filter { it != selectedAirport }
            .map { destination ->
                Flight(
                    id = destination.id,
                    departure = selectedAirport,
                    destination = destination,
                    isFavorite = favoriteFlights.any { favorite ->
                        favorite.departure == selectedAirport && favorite.destination == destination
                    }
                )
            }
    }.listStateIn(viewModelScope)

    val uiState = combine(
        searchTextFlow,
        suggestionsFlow,
        selectedAirportFlow,
        airportRoutesFlow,
        favoritesFlow,
    ) { searchText, suggestions, selectedAirport, routes, favorites ->
        HomeScreenUiState(
            searchText = searchText,
            airportSuggestions = suggestions,
            selectedAirport = selectedAirport,
            routes = routes,
            favoriteFlights = favorites,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUiState(),
    )

    fun setSearchText(text: String) {
        searchTextFlow.update { text }
    }

    fun setSelectedAirport(airport: Airport?) {
        selectedAirportFlow.update { airport }
    }

    fun onFavoriteClicked(flight: Flight) = viewModelScope.launch {
        favoriteRepository.toggleFavorite(flight)
    }

}