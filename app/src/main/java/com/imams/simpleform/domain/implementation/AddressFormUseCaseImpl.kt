package com.imams.simpleform.domain.implementation

import com.imams.simpleform.data.mapper.Mapper.toAddressInfo
import com.imams.simpleform.data.mapper.Mapper.toEntityWith
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toPersonalInfo
import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.repository.ProvinceDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.domain.usecase.AddressFormUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddressFormUseCaseImpl @Inject constructor(
    private val repository: RegistrationDataRepository,
    private val provinceRepo: ProvinceDataRepository
): AddressFormUseCase {

    private var _data: RegistrationInfo? = null

    override fun getPersonalInfo(id: String): Flow<PersonalInfo> {
        return repository.getRegistrationData(id).map {
        _data = it.toModel()
            it.toPersonalInfo()
        }
    }

    override fun getAddressInfo(id: String): Flow<AddressInfo> {
        return repository.getRegistrationData(id).map { it.toAddressInfo() }
    }

    override suspend fun getProvinces(): Flow<List<Province>> = provinceRepo.getProvinces()
        .flowOn(Dispatchers.IO)

    override suspend fun saveAddressInfo(addressInfo: AddressInfo, personalInfo: PersonalInfo) {
        val submitData = personalInfo.toEntityWith(addressInfo).apply {
            isSubmit = _data?.isSubmit
        }
        repository.saveRegistrationData(submitData)
    }

}