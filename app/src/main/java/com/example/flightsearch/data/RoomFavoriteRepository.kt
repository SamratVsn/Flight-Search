package com.example.flightsearch.data

import com.example.flightsearch.domain.model.Flight
import com.example.flightsearch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoomFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    private val favoriteMutex = Mutex()

    override suspend fun createFavorite(flight: Flight) = favoriteDao.insert(
        flight.copy(id = 0).toFavorite()
    )

    override suspend fun getFavoriteByRoute(
        departureCode: String,
        destinationCode: String
    ): Flight? = favoriteDao.getFavoriteByRoute(departureCode, destinationCode)?.toFlight()

    override fun getFavoritesFlow(): Flow<List<Flight>> {
        return favoriteDao.getFavoriteRoutes().map {
            it.map { favoriteRoute ->
                Flight(
                    id = favoriteRoute.favorite.id,
                    departure = favoriteRoute.departure.toDomain(),
                    destination = favoriteRoute.destination.toDomain(),
                    isFavorite = true,
                )
            }
        }
    }

    override suspend fun toggleFavorite(flight: Flight) = favoriteMutex.withLock {
        val favorite = getFavoriteByRoute(flight.departure.iata, flight.destination.iata)

        if (favorite == null) {
            createFavorite(flight)
        } else {
            removeFavorite(flight.copy(id = favorite.id))
        }
    }

    override suspend fun removeFavorite(flight: Flight) = favoriteDao.delete(flight.toFavorite())

}