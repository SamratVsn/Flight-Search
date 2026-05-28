package com.example.flightsearch.data

import android.content.Context
import com.example.flightsearch.domain.repository.AirportRepository
import com.example.flightsearch.domain.repository.FavoriteRepository

interface AppContainer {

    val airportRepository: AirportRepository

    val favoriteRepository: FavoriteRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    override val airportRepository: AirportRepository by lazy {
        RoomAirportRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }

    override val favoriteRepository: FavoriteRepository by lazy {
        RoomFavoriteRepository(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}