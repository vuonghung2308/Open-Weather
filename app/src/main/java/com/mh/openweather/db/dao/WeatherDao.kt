package com.mh.openweather.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mh.openweather.vo.entity.Weather

@Dao
abstract class WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(weathers: List<Weather>)

    @Query("select * from weather where id = :id")
    abstract fun get(id: Int): LiveData<Weather>

    fun get(ids: List<Int>): LiveData<List<Weather>> {
        val result = MediatorLiveData<List<Weather>>()
        val weathers = arrayListOf<Weather>()
        ids.forEach { id ->
            val weatherSource = get(id)
            result.addSource(weatherSource) { weather ->
                result.removeSource(weatherSource)
                weathers.add(weather)
                if (weathers.size == ids.size) {
                    result.value = weathers
                }
            }
        }
        return result
    }
}