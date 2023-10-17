package com.example.data.di

import com.example.data.dataSource.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 2023-10-17
 * pureum
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): DataSource =
        retrofit.create(DataSource::class.java)

}