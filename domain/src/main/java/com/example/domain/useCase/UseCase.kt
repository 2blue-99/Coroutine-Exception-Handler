package com.example.domain.useCase

import com.example.domain.model.MyTestData
import com.example.domain.repo.Repo
import com.example.domain.state.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
* 2023-10-10
* pureum
*/class UseCase(
    private val repo: Repo
) {
    operator fun invoke(placeName: String): Flow<ResourceState<MyTestData>> {
        return flow {
            emit(ResourceState.Loading())
            emit(repo.getAPIRepo(placeName))
        }
    }
}