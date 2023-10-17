package com.example.domain.repo

import com.example.domain.model.MyTestData
import com.example.domain.state.ResourceState

/**
 * 2023-10-10
 * pureum
 */
interface Repo {
    suspend fun getAPIRepo(id: String): ResourceState<MyTestData>
}