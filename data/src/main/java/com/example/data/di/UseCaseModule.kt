package com.example.data.di

import com.example.data.repoImpl.RepoImpl
import com.example.domain.repo.Repo
import com.example.domain.useCase.UseCase
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
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetApiUseCase(repo : Repo) : UseCase =
        UseCase(repo)
}