package com.example.flightsearch.domain.model

val dummyFlight = Flight(
    id = -1,
    departure = dummyAirport,
    destination = dummyAirport,
    isFavorite = false,
)


data class Flight(

    val id: Int,

    val departure: Airport,

    val destination: Airport,

    val isFavorite: Boolean,
)