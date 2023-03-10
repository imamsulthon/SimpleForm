package com.imams.simpleform.domain

import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
import kotlinx.coroutines.flow.Flow

interface AddressFormUseCase {

    fun getPersonalInfo(id: String): Flow<PersonalInfo>

    fun getAddressInfo(id: String): Flow<AddressInfo>

    suspend fun getProvinces(): Flow<List<Province>>

    suspend fun saveAddressInfo(addressInfo: AddressInfo, personalInfo: PersonalInfo)

}