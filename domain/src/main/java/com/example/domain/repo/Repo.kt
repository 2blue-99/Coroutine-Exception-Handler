package com.example.domain.repo

import com.example.domain.model.MyData
import com.example.domain.state.ResourceState
import kotlinx.coroutines.flow.Flow

/**
 * 2023-10-10
 * pureum
 */
interface Repo {
    suspend fun getAPIRepo(id: String): ResourceState<MyData>
}