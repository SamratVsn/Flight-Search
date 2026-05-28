package com.example.flightsearch.data

import androidx.room3.Dao
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport WHERE name LIKE :name OR iata_code LIKE :name ORDER BY passengers DESC")
    fun getAirportsByName(name: String): Flow<List<AirportEntity>>

    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAll(): Flow<List<AirportEntity>>
}