package com.thelegendofawizard.climatetoday.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherDao
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherEntry
import com.thelegendofawizard.climatetoday.utils.StringListConverter


@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class ClimateTodayDatabase:RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object{
        @Volatile private var instance:ClimateTodayDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ClimateTodayDatabase::class.java, "climate_today.db")
            .build()
    }
}