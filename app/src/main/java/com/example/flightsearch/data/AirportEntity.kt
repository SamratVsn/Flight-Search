package com.example.flightsearch.data

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.example.flightsearch.domain.model.Airport

@Entity(
    tableName = "airport",
    indices = [
        Index(value = ["iata_code"], unique = true)
    ]
)
data class AirportEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "iata_code")
    val iata: String,

    val name: String,

    @ColumnInfo(name = "passengers")
    val passengerCount: Int,
) {

    fun toDomain() = Airport(
        id = this.id,
        iata = this.iata,
        name = this.name,
        annualPassengers = this.passengerCount,
    )
}