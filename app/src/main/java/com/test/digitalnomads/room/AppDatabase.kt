package com.test.digitalnomads.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.digitalnomads.entities.News
import com.test.digitalnomads.entities.TypeConverter

@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val DATABASE = "database"
    }

    abstract fun newsDao(): NewsDao
}