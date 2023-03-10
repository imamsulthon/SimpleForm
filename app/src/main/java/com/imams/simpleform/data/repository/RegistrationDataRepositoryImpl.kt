package com.imams.simpleform.data.repository

import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toPersonalInfo
import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.source.local.RegistrationDao
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegistrationDataRepositoryImpl @Inject constructor(
    private val registrationDao: RegistrationDao,
): RegistrationDataRepository {

    override fun getPersonalInfo(id: String): Flow<PersonalInfo> {
        return registrationDao.getUserById(id).map { it.toPersonalInfo() }
            .flowOn(Dispatchers.IO).catch {
                printLog("getPersonalInfo catch ${it.message}")
            }    }

    override suspend fun savePersonalInfo(data: PersonalInfo) {
        registrationDao.addUser(data.toEntity())
    }

    override fun getAddressInfo(id: String): Flow<AddressInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAddressInfo(data: AddressInfo) {

    }

    override fun getCompleteRegistrationData(id: String): Flow<RegistrationInfo> {
        return registrationDao.getUserById(id).map { it.toModel() }
    }

    override suspend fun saveCompleteRegistration(data: FormDataEntity) {
        registrationDao.addUser(data)
    }

    override fun getAllCompleteUsers() {
        TODO("Not yet implemented")
    }

    private fun printLog(msg: String, tag: String? = "RegistrationRepo") {
        println("$tag: msg -> $msg")
    }

}