package com.mh.openweather.vo.result

import com.google.gson.annotations.SerializedName
import com.mh.openweather.vo.entity.City
import com.mh.openweather.vo.entity.Weather
import com.mh.openweather.vo.response.Clouds
import com.mh.openweather.vo.response.Coordinate
import com.mh.openweather.vo.response.MainInfo
import com.mh.openweather.vo.response.Wind

data class WeatherResult(
    val id: Int,
    val base: String,
    val visibility: Int,
    val dt: Long,
    val timezone: Long,
    val name: String,
    val coordinate: Coordinate,
    val wind: Wind,
    val mainInfo: MainInfo,
    val clouds: Clouds,
    val weathers: List<Weather>,
    val city: City
)