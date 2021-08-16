package com.mh.openweather.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mh.openweather.vo.entity.CityWeather

@Dao
abstract class CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(cityWeather: CityWeather)

    @Query("select * from city_weather where id = :id")
    abstract fun get(id: Int): LiveData<CityWeather>
}