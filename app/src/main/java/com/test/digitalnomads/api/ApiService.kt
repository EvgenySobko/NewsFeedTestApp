package com.test.digitalnomads.api

import com.test.digitalnomads.entities.News
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String? = "android",
        @Query("from") from: String? = "2019-04-00",
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("apiKey") apiKey: String? = "26eddb253e7840f988aec61f2ece2907",
        @Query("page") pageNumber: String
    ): News
}