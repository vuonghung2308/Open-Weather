package com.mh.openweather.api

import com.google.gson.Gson
import com.mh.openweather.vo.BaseResponse
import retrofit2.Response
import timber.log.Timber

sealed class ApiResponse<T> {
    data class ApiEmptyResponse<T>(
        val code: Int = -1,
        val message: String = "empty response"
    ) : ApiResponse<T>()

    data class ApiErrorResponse<T>(
        val code: Int,
        val message: String
    ) : ApiResponse<T>()

    data class ApiSuccessResponse<T>(
        val code: Int,
        val body: T
    ) : ApiResponse<T>()

    companion object {
        fun <T> create(throwable: Throwable): ApiResponse<T> {
            return ApiErrorResponse(
                code = 0,
                message = throwable.message ?: "unknown error"
            )
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            if (response.isSuccessful) {
                val code = response.code()
                val body = response.body()
                if (code == 204 || body == null)
                    return ApiEmptyResponse()
                return ApiSuccessResponse(code, body)
            } else {
                val code = response.code()
                val message = response.errorBody()?.string() ?: response.message()
                try {
                    val errorBody = Gson().fromJson(message, BaseResponse::class.java)
                    return ApiEmptyResponse(errorBody.code!!, errorBody.message!!)
                } catch (e: Exception) {
                    Timber.e(e)
                }
                return ApiErrorResponse(code, message)
            }
        }
    }
}