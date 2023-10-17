package com.example.data.di

import com.example.data.dataSource.DataSource
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 2023-10-17
 * pureum
 */
object ServiceModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): DataSource =
        retrofit.create(DataSource::class.java)

}