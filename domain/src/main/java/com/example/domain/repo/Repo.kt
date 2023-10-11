package com.example.domain.repo

import com.example.domain.model.MyData
import kotlinx.coroutines.flow.Flow

/**
 * 2023-10-10
 * pureum
 */
interface Repo {
    suspend fun getAPIRepo(id: Int): MyData

    fun getAPIFlowRepo(): Flow<MyData>


}