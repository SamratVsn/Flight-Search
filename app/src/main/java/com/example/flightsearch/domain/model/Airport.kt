package com.example.flightsearch.domain.model

val dummyAirport = Airport(
    id = -1,
    iata = "DMY",
    name = "Dummy International Airport",
    annualPassengers = -1,
)

data class Airport(

    val id: Int,

    val iata: String,

    val name: String,

    val annualPassengers: Int,
)