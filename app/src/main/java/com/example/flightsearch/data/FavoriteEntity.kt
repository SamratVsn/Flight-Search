package com.example.flightsearch.data

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.example.flightsearch.domain.model.Flight

@Entity(
    tableName = "favorite",
    indices = [
        Index(value = ["departure_code", "destination_code"], unique = true)
    ]
)
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "departure_code")
    val departureCode: String,

    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)

fun Flight.toFavorite(id: Int = this.id) = FavoriteEntity(
    id = id,
    departureCode = departure.iata,
    destinationCode = destination.iata,
)