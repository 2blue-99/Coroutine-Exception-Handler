package com.example.data.di

import com.example.data.util.NetworkInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 2023-10-10
 * pureum
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val PRIVATE_KEY = "575a7a59456c706d34374c474c676c"

    @Singleton
    @Provides
    fun provideInterceptor(): NetworkInterceptor = NetworkInterceptor()

    @Singleton
    @Provides
    fun provideOkhttpApi(networkInterceptor: NetworkInterceptor): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                val response = it.proceed(request)
                response
            }
            .addInterceptor(networkInterceptor)
            .connectTimeout(300, TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(interceptorClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://openapi.seoul.go.kr:8088/${PRIVATE_KEY}/xml/")
            .client(interceptorClient)
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .build()
}