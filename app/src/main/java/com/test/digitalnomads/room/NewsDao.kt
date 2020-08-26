package com.test.digitalnomads.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.digitalnomads.entities.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

}