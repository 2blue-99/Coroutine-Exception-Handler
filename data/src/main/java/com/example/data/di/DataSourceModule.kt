package com.example.data.di

import com.example.data.dataSource.DataSource
import com.example.data.dataSourceImpl.DataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 2023-10-10
 * pureum
 */

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideCardDataSource(retrofit: DataSource): DataSourceImpl = DataSourceImpl(retrofit)
}