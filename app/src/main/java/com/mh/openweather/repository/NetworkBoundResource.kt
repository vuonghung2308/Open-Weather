package com.mh.openweather.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mh.openweather.AppScope
import com.mh.openweather.api.ApiResponse
import com.mh.openweather.api.ApiResponse.*
import com.mh.openweather.vo.Resource
import kotlinx.coroutines.launch

abstract class NetworkBoundResource<RequestType, ResultType>
@MainThread constructor(
    private val appScope: AppScope
) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        setValue(Resource.loading())
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }


    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(dbSource) { data ->
            setValue(Resource.loading(data))
        }
        val apiSource = createCall()
        result.addSource(apiSource) { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    appScope.ioScope.launch {
                        saveResponse(processResponse(apiResponse))
                        appScope.mainScope.launch {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.error(apiResponse.message, newData))
                    }
                }
                else -> {
                    apiResponse as ApiEmptyResponse
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.error(apiResponse.message, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected abstract fun saveResponse(response: RequestType)

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) =
        response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    abstract fun shouldFetch(data: ResultType?): Boolean
}