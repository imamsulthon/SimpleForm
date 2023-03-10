package com.imams.simpleform.data.repository

import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import kotlinx.coroutines.flow.Flow

interface RegistrationDataRepository{

    fun getCompleteRegistrationData(id: String): Flow<FormDataEntity>

    suspend fun saveCompleteRegistration(data: FormDataEntity)

    fun getAllCompleteUsers(): Flow<List<RegistrationInfo>>

    suspend fun clearAllData()

}