package com.mh.openweather.util

import androidx.lifecycle.LiveData
import com.mh.openweather.api.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataApiResponse<T>(
    private val call: Call<T>
) : LiveData<ApiResponse<T>>() {
    private val started = AtomicBoolean(false)
    private val callback = object : Callback<T> {
        override fun onFailure(call: Call<T>, throwable: Throwable) {
            postValue(ApiResponse.create(throwable))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            postValue(ApiResponse.create(response))
        }
    }

    override fun onActive() {
        super.onActive()
        if (started.compareAndSet(false, true)) {
            call.enqueue(callback)
        }
    }
}