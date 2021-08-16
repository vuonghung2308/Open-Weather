package com.mh.openweather.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mh.openweather.db.AppDatabase
import com.mh.openweather.vo.entity.City
import com.mh.openweather.vo.entity.CityWeather
import com.mh.openweather.vo.entity.Weather
import com.mh.openweather.vo.response.WeatherResponse
import com.mh.openweather.vo.result.WeatherResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchWeatherDao @Inject constructor(
    private val database: AppDatabase,
    private val cityDao: CityDao,
    private val searchDao: SearchDao,
    private val weatherDao: WeatherDao,
    private val cityWeatherDaoDao: CityWeatherDao
) {
    fun saveResponse(key: String, response: WeatherResponse) {
        val search = searchDao.get(response.id, key)
        database.runInTransaction {
            searchDao.insert(search)
            cityDao.insert(response.city)
            weatherDao.insert(response.weathers)
            cityWeatherDaoDao.insert(getCityWeather(response))
        }
    }

    fun weather(query: String): LiveData<WeatherResult> {
        val result = MediatorLiveData<WeatherResult>()
        val searchSource = searchDao.get(query)
        result.addSource(searchSource) { id ->
            result.removeSource(searchSource)
            if (id == -1) {
                result.value = result.value
                return@addSource
            }
            val cityWeatherSource = cityWeatherDaoDao.get(id)
            result.addSource(cityWeatherSource) { cityWeather ->
                result.removeSource(cityWeatherSource)
                val citySource = cityDao.get(cityWeather.city)
                result.addSource(citySource) { city ->
                    result.removeSource(citySource)
                    val weathersSource = weatherDao.get(cityWeather.weathers)
                    result.addSource(weathersSource) { weathers ->
                        result.removeSource(weathersSource)
                        result.value = getResult(cityWeather, city, weathers)
                    }
                }
            }
        }
        return result
    }

    private fun getResult(
        cityWeather: CityWeather,
        city: City,
        weathers: List<Weather>
    ): WeatherResult {
        return WeatherResult(
            cityWeather.id, cityWeather.base,
            cityWeather.visibility, cityWeather.dt,
            cityWeather.timezone, cityWeather.name,
            cityWeather.coordinate, cityWeather.wind,
            cityWeather.mainInfo, cityWeather.clouds,
            weathers, city
        )
    }

    private fun getCityWeather(response: WeatherResponse): CityWeather {
        val weathers = arrayListOf<Int>()
        response.weathers.forEach { weather ->
            weathers.add(weather.id)
        }
        return CityWeather(
            response.id, response.base,
            response.visibility, response.dt,
            response.timezone, response.name,
            response.coordinate, response.wind,
            response.mainInfo, response.clouds,
            weathers, response.city.id
        )
    }
}