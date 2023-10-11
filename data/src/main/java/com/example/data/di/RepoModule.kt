package com.example.data.di

import com.example.data.dataSource.DataSource
import com.example.data.dataSourceImpl.DataSourceImpl
import com.example.data.repoImpl.RepoImpl
import com.example.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 2023-10-10
 * pureum
 */

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideCardRepoData(source: DataSourceImpl): Repo = RepoImpl(source)

}