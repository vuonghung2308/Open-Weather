package com.mh.openweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mh.openweather.repository.WeatherRepository
import com.mh.openweather.vo.Resource
import com.mh.openweather.vo.result.WeatherResult
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {
    private val query = MutableLiveData<String>()
    val weather: LiveData<Resource<WeatherResult>>
        get() = query.switchMap { repo.getWeather(it) }

    fun getWeather(city: String) {
        if (query.value != city) {
            query.value = city
        }
    }
}