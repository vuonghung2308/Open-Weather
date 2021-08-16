package com.mh.openweather.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class Search(
    @PrimaryKey
    val id: Int,
    val keys: List<String>
)