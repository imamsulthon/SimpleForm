package com.imams.simpleform.data.repository

import com.imams.simpleform.data.model.Province
import kotlinx.coroutines.flow.Flow

interface ProvinceDataRepository {

    suspend fun getProvinces(): Flow<List<Province>>

    suspend fun getProvinceRemote(): Flow<List<Province>>

    suspend fun getProvincesLocal(): Flow<List<Province>>
}