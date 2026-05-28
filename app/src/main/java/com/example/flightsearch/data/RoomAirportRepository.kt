package com.example.flightsearch.data

import com.example.flightsearch.domain.model.Airport
import com.example.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAirportRepository(private val airportDao: AirportDao) : AirportRepository {
    override fun searchAirportsFlow(query: String): Flow<List<Airport>> {
        return airportDao.getAirportsByName(query).map { airports ->
            airports.map { it.toDomain() }
        }
    }

    override fun getAirportsFlow(): Flow<List<Airport>> = airportDao.getAll().map { airports ->
        airports.map { it.toDomain() }
    }
}