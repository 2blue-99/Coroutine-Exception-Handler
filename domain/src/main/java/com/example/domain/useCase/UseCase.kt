package com.example.domain.useCase

import com.example.domain.model.MyData
import com.example.domain.repo.Repo

/**
* 2023-10-10
* pureum
*/class UseCase(
    private val repo: Repo
) {
    suspend operator fun invoke(id: Int): MyData {
        return repo.getAPIRepo(id)
    }
}