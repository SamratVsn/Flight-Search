package com.example.flightsearch.data

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.flightsearch.domain.model.Flight

data class FavoriteRoute(

    @Embedded
    val favorite: FavoriteEntity,

    @Relation(
        parentColumns = ["departure_code"],
        entityColumns = ["iata_code"]
    )
    val departure: AirportEntity,

    @Relation(
        parentColumns = ["destination_code"],
        entityColumns = ["iata_code"]
    )
    val destination: AirportEntity,
) {
    fun toFlight() = Flight(
        id = favorite.id,
        departure = departure.toDomain(),
        destination = destination.toDomain(),
        isFavorite = true,
    )
}