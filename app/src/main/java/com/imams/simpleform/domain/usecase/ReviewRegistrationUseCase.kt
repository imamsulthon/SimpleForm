package com.imams.simpleform.domain.usecase

import com.imams.simpleform.data.model.RegistrationInfo
import kotlinx.coroutines.flow.Flow


interface ReviewRegistrationUseCase {

    suspend fun getRegistrationData(id: String): Flow<RegistrationInfo>

    suspend fun saveRegistrationForm(data: RegistrationInfo)

}