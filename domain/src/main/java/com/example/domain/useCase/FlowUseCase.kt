package com.example.domain.useCase

import com.example.domain.model.MyData
import com.example.domain.repo.Repo
import kotlinx.coroutines.flow.Flow

/**
* 2023-10-10
* pureum
*/class FlowUseCase(
    private val repo: Repo
) {
    operator fun invoke(): Flow<MyData> =
        repo.getAPIFlowRepo()

}