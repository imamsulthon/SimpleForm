package com.imams.simpleform.domain

import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toPersonalInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.RegistrationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FormPersonalInfoUseCaseImpl @Inject constructor(
    private val repository: RegistrationDataRepository
): FormPersonalInfoUseCase {

    private var _data: RegistrationInfo? = null

    override fun getPersonalInfo(id: String): Flow<PersonalInfo> {
        return repository.getCompleteRegistrationData(id).map {
            _data = it.toModel()
            it.toPersonalInfo()
        }
    }

    override suspend fun savePersonalInfo(data: PersonalInfo) {
        val info = data.toEntity()
        _data?.let {
            info.apply {
                address = it.address
                addressNo = it.addressNo
                housing = it.houseType
                province = it.province
            }
        }
        repository.saveCompleteRegistration(info)
    }

}