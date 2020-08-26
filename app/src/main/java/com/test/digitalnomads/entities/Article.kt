package com.test.digitalnomads.entities

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

sealed class ListItem {

    data class Article(

        @SerializedName("author")
        val author: String,

        @SerializedName("content")
        val content: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("publishedAt")
        val publishedAt: String,

        @SerializedName("source")
        val source: Source,

        @PrimaryKey(autoGenerate = true)
        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("urlToImage")
        val urlToImage: String
    ): ListItem()

    object Footer : ListItem()
}