package com.mh.openweather.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("sunrise")
    val sunrise: Long
)