package com.mh.openweather.vo.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mh.openweather.vo.response.Clouds
import com.mh.openweather.vo.response.Coordinate
import com.mh.openweather.vo.response.MainInfo
import com.mh.openweather.vo.response.Wind

@Entity(
    tableName = "city_weather",
    foreignKeys = [
        ForeignKey(
            entity = Search::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        ),
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city"]
        )
    ]
)
data class CityWeather(
    @PrimaryKey
    val id: Int,
    val base: String,
    val visibility: Int,
    val dt: Long,
    val timezone: Long,
    val name: String,
    @Embedded
    val coordinate: Coordinate,
    @Embedded
    val wind: Wind,
    @Embedded
    val mainInfo: MainInfo,
    @Embedded
    val clouds: Clouds,
    val weathers: List<Int>,
    val city: Int
)