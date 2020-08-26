package com.test.digitalnomads.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


@Entity
data class News(

    @SerializedName("articles")
    val articles: List<ListItem.Article>,

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int
)

class TypeConverter {
    @TypeConverter
    fun listToString(articles: List<ListItem.Article>?): String? {
        if (articles == null) return null
        val gson = Gson()
        return gson.toJson(articles)
    }

    @TypeConverter
    fun listFromString(articlesJson: String?): List<ListItem.Article>? {
        if (articlesJson == null) return null
        return Gson().fromJson(articlesJson, object: TypeToken<List<ListItem.Article>>(){}.type)
    }

    @TypeConverter
    fun articleToString(article: ListItem.Article?): String? {
        if (article == null) return null
        val gson = Gson()
        return gson.toJson(article)
    }

    @TypeConverter
    fun articleFromString(article: String?): ListItem.Article? {
        if (article == null) return null
        val gson = Gson()
        return gson.fromJson(article, ListItem.Article::class.java)
    }
}