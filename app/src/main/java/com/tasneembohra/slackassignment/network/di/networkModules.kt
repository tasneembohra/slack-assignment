package com.tasneembohra.slackassignment.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tasneembohra.slackassignment.network.api.UserSearchService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber

private const val API_URL = "https://slack-users.herokuapp.com/"

@OptIn(ExperimentalSerializationApi::class)
val networkModules = module {
    factory {
        val logger = HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").d(message) }
        HttpLoggingInterceptor(logger = logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory {
        Json.asConverterFactory("application/json".toMediaType())
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    factory<UserSearchService> {
        get<Retrofit>().create()
    }
}