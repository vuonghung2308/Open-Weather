package com.mh.openweather.di

import android.app.Application
import androidx.room.Room
import com.mh.openweather.api.ApiConstants
import com.mh.openweather.api.OpenWeatherServices
import com.mh.openweather.db.AppDatabase
import com.mh.openweather.db.dao.CityDao
import com.mh.openweather.db.dao.CityWeatherDao
import com.mh.openweather.db.dao.SearchDao
import com.mh.openweather.db.dao.WeatherDao
import com.mh.openweather.di.view.ActivityModule
import com.mh.openweather.di.viewmodel.ViewModelModule
import com.mh.openweather.util.ApiKeyInterceptor
import com.mh.openweather.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ActivityModule::class,
        ViewModelModule::class
    ]
)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val apiKeyInterceptor = ApiKeyInterceptor(
            ApiConstants.NAME,
            ApiConstants.KEY
        )
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherServices(retrofit: Retrofit): OpenWeatherServices {
        return retrofit.create(OpenWeatherServices::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }

    @Provides
    @Singleton
    fun provideSearchDao(database: AppDatabase): SearchDao {
        return database.searchDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideCityWeatherDao(database: AppDatabase): CityWeatherDao {
        return database.cityWeather()
    }
}