package com.mh.openweather.vo.response

import com.google.gson.annotations.SerializedName
import com.mh.openweather.vo.BaseResponse
import com.mh.openweather.vo.entity.City
import com.mh.openweather.vo.entity.Weather

data class WeatherResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("base")
    val base: String,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("timezone")
    val timezone: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coordinate: Coordinate,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("main")
    val mainInfo: MainInfo,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("weather")
    val weathers: List<Weather>,
    @SerializedName("sys")
    val city: City
) : BaseResponse()