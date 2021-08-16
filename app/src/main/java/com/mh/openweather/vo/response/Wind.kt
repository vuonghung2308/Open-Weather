package com.mh.openweather.vo.response

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Float,
    @SerializedName("deg")
    val degree: Int,
    @SerializedName("gust")
    val gust: Float
)