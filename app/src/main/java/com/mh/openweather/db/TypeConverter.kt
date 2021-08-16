package com.mh.openweather.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun intsToJson(ints: List<Int>): String {
        return Gson().toJson(ints)
    }

    @TypeConverter
    fun jsonToInts(json: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun stringsToJson(strings: List<String>): String {
        return Gson().toJson(strings)
    }

    @TypeConverter
    fun jsonToStrings(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}