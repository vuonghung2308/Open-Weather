package com.mh.openweather.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mh.openweather.vo.entity.Search

@Dao
abstract class SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(search: Search)

    @Query("select * from search")
    abstract fun get(): LiveData<List<Search>>

    @Query("select * from search where id = :id")
    abstract fun get(id: Int): Search?

    fun get(key: String): LiveData<Int> {
        return get().map { searchs ->
            var id = -1
            searchs.forEach { search ->
                if (search.keys.contains(key)) {
                    id = search.id
                }
            }
            return@map id
        }
    }

    fun get(id: Int, key: String): Search {
        val search = get(id)
        val keys = arrayListOf<String>()
        search?.keys?.let { keys.addAll(it) }
        if (!keys.contains(key)) {
            keys.add(key)
        }
        return Search(id, keys)
    }
}