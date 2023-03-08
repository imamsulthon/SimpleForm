package com.imams.simpleform.data.repository

import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.model.PersonalInfo
import kotlinx.coroutines.flow.Flow

interface RegistrationDataRepository{

    fun getPersonalInfo(id: Int): Flow<PersonalInfo>

    suspend fun savePersonalInfo(data: PersonalInfo)

    fun getAddressInfo()

    suspend fun saveAddressInfo()

    fun getCompleteRegistrationData(id: Int): Flow<PersonalInfo>

    suspend fun saveCompleteRegistration(data: FormDataEntity)

    fun getAllCompleteUsers()

}