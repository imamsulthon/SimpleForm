package com.imams.simpleform.domain.implementation

import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toPersonalInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.domain.usecase.FormPersonalInfoUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FormPersonalInfoUseCaseImpl @Inject constructor(
    private val repository: RegistrationDataRepository
): FormPersonalInfoUseCase {

    private var _data: RegistrationInfo? = null

    override fun getPersonalInfo(id: String): Flow<PersonalInfo> {
        return repository.getRegistrationData(id).map {
            _data = it.toModel()
            it.toPersonalInfo()
        }
    }

    override suspend fun savePersonalInfo(data: PersonalInfo) {
        val submitData = data.toEntity()
        _data?.let {
            submitData.apply {
                address = it.address
                addressNo = it.addressNo
                housing = it.houseType
                province = it.province
            }
        }
        repository.saveRegistrationData(submitData)
    }

}