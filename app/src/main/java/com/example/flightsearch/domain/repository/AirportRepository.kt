package com.example.flightsearch.domain.repository

import com.example.flightsearch.domain.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun searchAirportsFlow(query: String): Flow<List<Airport>>

    fun getAirportsFlow(): Flow<List<Airport>>
}