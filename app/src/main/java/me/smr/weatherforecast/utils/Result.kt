package me.smr.weatherforecast.utils

sealed class Result<out T : Any> {
    object Loading : Result<Nothing>()
    class Value<out T : Any>(@JvmField val value: T) : Result<T>()
    class Error(@JvmField val error: Throwable) : Result<Nothing>()
}