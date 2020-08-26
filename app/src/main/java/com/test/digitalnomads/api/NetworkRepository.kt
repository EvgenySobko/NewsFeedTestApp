package com.test.digitalnomads.api

import com.test.digitalnomads.entities.News

interface NetworkRepository {

    suspend fun getNews(pageNumber: Int): News?

}

class NetworkRepoImpl(private val apiService: ApiService) : NetworkRepository {

    override suspend fun getNews(pageNumber: Int): News? {
        return apiService.getNews(pageNumber = "{$pageNumber}")
    }
}