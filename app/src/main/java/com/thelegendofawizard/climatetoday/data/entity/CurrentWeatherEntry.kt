package com.thelegendofawizard.climatetoday.data.entity

// @TypeConverters(StringListConverter.class)
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.thelegendofawizard.climatetoday.utils.StringListConverter

const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloudcover: Int,
    val feelslike: Int,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: String,
    val precip: Double,
    val pressure: Int,
    val temperature: Int,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_descriptions")
        val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_WEATHER_ID
}