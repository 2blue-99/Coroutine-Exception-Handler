package com.example.data.repoImpl

import com.example.data.dataSource.DataSource
import com.example.data.dataSourceImpl.DataSourceImpl
import com.example.data.model.ServerResponse
import com.example.data.model.toMyData
import com.example.domain.model.MyData
import com.example.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 2023-10-10
 * pureum
 */
class RepoImpl @Inject constructor(
    private val dataSource: DataSourceImpl
) : Repo {
    override suspend fun getAPIRepo(id: Int): MyData {
        return dataSource.getApiDataSource(id.toString()).toMyData()
    }

    override fun getAPIFlowRepo(): Flow<MyData> =
        dataSource.getApiFlowDataSource()
            .map { it.toMyData() }

}