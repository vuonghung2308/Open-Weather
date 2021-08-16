package com.mh.openweather.api

import androidx.lifecycle.LiveData
import com.mh.openweather.vo.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherServices {
    @GET("weather")
    fun weather(
        @Query("q") city: String
    ): LiveData<ApiResponse<WeatherResponse>>
}