package com.mh.openweather.util

import androidx.lifecycle.LiveData
import com.mh.openweather.api.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class LiveDataCallAdapter<T>(
    private val bodyType: Type
) : CallAdapter<T, LiveData<ApiResponse<T>>> {
    override fun responseType(): Type = bodyType

    override fun adapt(call: Call<T>): LiveData<ApiResponse<T>> {
        return LiveDataApiResponse(call)
    }
}