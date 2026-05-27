package com.example.flightsearch.data

import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

interface AirportDao {

    @Query("""
       SELECT * FROM airport
       WHERE name LIKE "%" 
       OR iata_code LIKE "%"
       ORDER BY passengers DESC
    """)
    fun searchAirports(searchText: String): Flow<List<Airport>>

    @Query("""
        SELECT * FROM airport
        WHERE iata_code != :iataCode
        ORDER BY passengers DESC
    """)
    fun getDestinationAirport(iatacode: String) : Flow<List<Airport>>

    @Query("""
          SELECT * FROM airport
          WHERE iata_code = :iataCode
          LIMIT 1
    """)
    suspend fun getAirportCode(iatacode: String): Airport?
}