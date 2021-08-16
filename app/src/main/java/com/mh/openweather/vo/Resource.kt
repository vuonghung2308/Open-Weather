package com.mh.openweather.vo

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data
            )
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )
        }
    }
}