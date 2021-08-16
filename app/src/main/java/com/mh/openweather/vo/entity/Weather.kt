package com.mh.openweather.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)