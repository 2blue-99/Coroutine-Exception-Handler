package com.example.data.repoImpl

import com.example.data.dataSourceImpl.DataSourceImpl
import com.example.data.mapper.DataMapper
import com.example.domain.model.MyData
import com.example.domain.repo.Repo
import com.example.domain.state.ResourceState
import javax.inject.Inject

/**
 * 2023-10-10
 * pureum
 */
class RepoImpl @Inject constructor(
    private val dataSource: DataSourceImpl
) : Repo {
    override suspend fun getAPIRepo(id: String): ResourceState<MyData> {
        return when(val response = dataSource.getApiDataSource(id)){
            is ResourceState.Success -> ResourceState.Success(data = DataMapper.mapperToMyData(response.data))
            else -> response as ResourceState<MyData>
        }
    }
}