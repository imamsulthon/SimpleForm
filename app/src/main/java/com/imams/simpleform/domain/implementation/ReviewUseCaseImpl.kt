package com.imams.simpleform.domain.implementation

import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.domain.usecase.ReviewRegistrationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewUseCaseImpl @Inject constructor(
    private val repository: RegistrationDataRepository,
): ReviewRegistrationUseCase {

    override suspend fun getRegistrationData(id: String): Flow<RegistrationInfo> {
        return repository.getRegistrationData(id).map { it.toModel() }
    }

    override suspend fun saveRegistrationForm(data: RegistrationInfo) {
        repository.saveRegistrationData(data.toEntity(true))
    }

}