package com.mh.openweather.repository

import androidx.lifecycle.LiveData
import com.mh.openweather.AppScope
import com.mh.openweather.api.ApiResponse
import com.mh.openweather.api.OpenWeatherServices
import com.mh.openweather.db.dao.SearchWeatherDao
import com.mh.openweather.vo.Resource
import com.mh.openweather.vo.response.WeatherResponse
import com.mh.openweather.vo.result.WeatherResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: OpenWeatherServices,
    private val dao: SearchWeatherDao,
    private val appScope: AppScope
) {
    fun getWeather(city: String): LiveData<Resource<WeatherResult>> {
        return object : NetworkBoundResource<WeatherResponse, WeatherResult>(
            appScope
        ) {
            override fun saveResponse(response: WeatherResponse) {
                dao.saveResponse(city, response)
            }

            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> {
                return api.weather(city)
            }

            override fun loadFromDb(): LiveData<WeatherResult> {
                return dao.weather(city)
            }

            override fun shouldFetch(data: WeatherResult?): Boolean {
                return true
            }
        }.asLiveData()
    }
}