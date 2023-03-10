package com.imams.simpleform.data.repository

import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import kotlinx.coroutines.flow.Flow

interface RegistrationDataRepository{

    fun getPersonalInfo(id: String): Flow<PersonalInfo>

    suspend fun savePersonalInfo(data: PersonalInfo)

    fun getAddressInfo(id: String): Flow<AddressInfo>

    suspend fun saveAddressInfo(data: AddressInfo)

    fun getCompleteRegistrationData(id: String): Flow<RegistrationInfo>

    suspend fun saveCompleteRegistration(data: FormDataEntity)

    fun getAllCompleteUsers(): Flow<List<RegistrationInfo>>

    suspend fun clearAllData()

}