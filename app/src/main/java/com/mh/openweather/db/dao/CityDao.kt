package com.mh.openweather.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mh.openweather.vo.entity.City

@Dao
abstract class CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(city: City)

    @Query("select * from city where id = :id")
    abstract fun get(id: Int): LiveData<City>
}