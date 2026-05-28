package com.example.flightsearch.domain.repository

import com.example.flightsearch.domain.model.Flight
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun createFavorite(flight: Flight)

    suspend fun getFavoriteByRoute(departureCode: String, destinationCode: String): Flight?

    fun getFavoritesFlow(): Flow<List<Flight>>

    suspend fun toggleFavorite(flight: Flight)

    suspend fun removeFavorite(flight: Flight)
}