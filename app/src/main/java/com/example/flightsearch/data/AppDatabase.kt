package com.example.flightsearch.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    version = 1,
    entities = [
        AirportEntity::class,
        FavoriteEntity::class,
    ],
    exportSchema = false,
)
abstract class FlightSearchDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search_database",
                )
                    .createFromAsset("database/FlightSearch.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}