package com.imams.simpleform.domain.usecase

import com.imams.simpleform.data.model.PersonalInfo
import kotlinx.coroutines.flow.Flow

interface FormPersonalInfoUseCase{

    fun getPersonalInfo(id: String): Flow<PersonalInfo>

    suspend fun savePersonalInfo(data: PersonalInfo)

}