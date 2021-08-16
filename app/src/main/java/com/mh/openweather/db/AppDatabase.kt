package com.mh.openweather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mh.openweather.db.dao.CityDao
import com.mh.openweather.db.dao.CityWeatherDao
import com.mh.openweather.db.dao.SearchDao
import com.mh.openweather.db.dao.WeatherDao
import com.mh.openweather.vo.entity.City
import com.mh.openweather.vo.entity.CityWeather
import com.mh.openweather.vo.entity.Search
import com.mh.openweather.vo.entity.Weather

@Database(
    entities = [
        City::class,
        Weather::class,
        Search::class,
        CityWeather::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun searchDao(): SearchDao
    abstract fun weatherDao(): WeatherDao
    abstract fun cityWeather(): CityWeatherDao
}