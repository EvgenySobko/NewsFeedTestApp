package com.test.digitalnomads.api

import com.test.digitalnomads.entities.News
import com.test.digitalnomads.room.NewsDao

interface Repository {

    suspend fun saveNews(news: News)

    suspend fun getNews(): News
}

class RepositoryImpl(private val newsDao: NewsDao): Repository {

    override suspend fun saveNews(news: News) {
        newsDao.insert(news)
    }

    override suspend fun getNews(): News {
        return newsDao.getAll()
    }
}