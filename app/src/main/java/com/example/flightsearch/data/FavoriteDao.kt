package com.example.flightsearch.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity)


    @Transaction
    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode LIMIT 1")
    suspend fun getFavoriteByRoute(departureCode: String, destinationCode: String): FavoriteRoute?

    @Transaction
    @Query("SELECT * FROM favorite")
    fun getFavoriteRoutes(): Flow<List<FavoriteRoute>>

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

}