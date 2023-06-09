package com.imams.simpleform.data.repository

import com.imams.simpleform.data.mapper.Mapper.toRegModels
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.source.local.dao.RegistrationDao
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegistrationDataRepositoryImpl @Inject constructor(
    private val registrationDao: RegistrationDao,
): RegistrationDataRepository {

    override fun getRegistrationData(id: String): Flow<FormDataEntity> {
        return registrationDao.getUserById(id).flowOn(Dispatchers.IO)
    }

    override suspend fun saveRegistrationData(data: FormDataEntity) {
        registrationDao.addUser(data)
    }

    override fun getAllCompleteRegistration(): Flow<List<RegistrationInfo>> {
        return registrationDao.getAllUser().map {it.toRegModels() }
    }

    override suspend fun clearAllData() {
        registrationDao.delete()
    }

}