package com.kdw.flomusic.network

import com.kdw.flomusic.BuildConfig
import com.kdw.flomusic.api.ApiService
import com.kdw.flomusic.util.Constants
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<ApiService> {
        Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .client(OkHttpClient.Builder().also { client ->
                    if(BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                        client.connectTimeout(100, TimeUnit.SECONDS)
                        client.readTimeout(100, TimeUnit.SECONDS)
                        client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    }
                }.build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}

var appModules = listOf(networkModule)