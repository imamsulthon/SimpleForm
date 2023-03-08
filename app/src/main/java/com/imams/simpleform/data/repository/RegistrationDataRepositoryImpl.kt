package com.imams.simpleform.data.repository

import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toPersonalInfo
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.source.local.RegistrationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RegistrationDataRepositoryImpl @Inject constructor(
    private val registrationDao: RegistrationDao,
): RegistrationDataRepository {

    override fun getPersonalInfo(id: Int): Flow<PersonalInfo> {
        return registrationDao.getUserById(id).map { it.toPersonalInfo() }
            .flowOn(Dispatchers.IO).catch {
                printLog("getPersonalInfo catch ${it.message}")
            }
    }

    override suspend fun savePersonalInfo(data: PersonalInfo) {
        registrationDao.addUser(data.toEntity())
    }

    override fun getAddressInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun saveAddressInfo() {
        TODO("Not yet implemented")
    }

    override fun getCompleteRegistrationData(id: Int): Flow<PersonalInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCompleteRegistration(data: FormDataEntity) {
        TODO("Not yet implemented")
    }

    override fun getAllCompleteUsers() {
        TODO("Not yet implemented")
    }

    private fun printLog(msg: String, tag: String? = "RegistrationRepo") {
        println("$tag: msg -> $msg")
    }

}