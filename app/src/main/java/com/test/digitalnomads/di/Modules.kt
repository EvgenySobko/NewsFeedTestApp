package com.test.digitalnomads.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.digitalnomads.BuildConfig
import com.test.digitalnomads.api.*
import com.test.digitalnomads.room.AppDatabase
import com.test.digitalnomads.room.NewsDao
import com.test.digitalnomads.ui.main.mvp.AbstractMainPresenter
import com.test.digitalnomads.ui.main.mvp.MainPresenterImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModules = module(override = true) {

    factory<AbstractMainPresenter> {
        MainPresenterImpl(
            get(),
            get()
        )
    }

    factory<Repository> { RepositoryImpl(get()) }

    single {
        Room
            .databaseBuilder(
                androidContext(), AppDatabase::class.java, AppDatabase.DATABASE
            ).build()
    }
    single<NewsDao> { get<AppDatabase>().newsDao() }

    single { provideInterceptor() }
    single { provideGson() }
    single { provideDefaultOkhttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }

    factory<NetworkRepository> { NetworkRepoImpl(get()) }

}

fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    return gsonBuilder.create()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return interceptor
}

fun provideDefaultOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}