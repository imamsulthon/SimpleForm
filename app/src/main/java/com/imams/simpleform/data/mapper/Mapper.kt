package com.imams.simpleform.data.mapper

import com.imams.simpleform.data.model.AddressInfo
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.data.model.RegistrationInfo
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.source.local.entity.ProvinceEntity
import com.imams.simpleform.data.source.remote.ProvinceResponse

object Mapper {

    fun FormDataEntity.toPersonalInfo() = PersonalInfo(
        id = id,
        fullName = fullName,
        bankAccount = bankAccount,
        education = education,
        dob = dob,
    )

    fun FormDataEntity.toAddressInfo() = AddressInfo(
        id = id,
        address = address.orEmpty(),
        addressNo = addressNo.orEmpty(),
        province = province.orEmpty(),
        houseType = housing.orEmpty(),
    )

    fun PersonalInfo.toEntity() = FormDataEntity(
        id = id,
        fullName = fullName,
        bankAccount = bankAccount,
        education = education,
        dob = dob,
        address = null,
        addressNo = null,
        province = null,
        housing = null,
        isSubmit = null,
    )

    fun PersonalInfo.toEntityWith(address: AddressInfo) = FormDataEntity(
        id = id,
        fullName = fullName,
        bankAccount = bankAccount,
        education = education,
        dob = dob,
        address = address.address,
        addressNo = address.addressNo,
        province = address.province,
        housing = address.houseType,
        isSubmit = false
    )

    fun List<FormDataEntity>.toRegModels(): List<RegistrationInfo> = this.map { it.toModel() }

    fun FormDataEntity.toModel() = RegistrationInfo(
        id = id,
        fullName = fullName,
        bankAccount = bankAccount,
        education = education,
        dob = dob,
        address = address.orEmpty(),
        addressNo = addressNo.orEmpty(),
        province = province.orEmpty(),
        houseType = housing.orEmpty(),
        isSubmit = isSubmit ?: false
    )

    fun RegistrationInfo.toEntity(submit: Boolean = false) = FormDataEntity(
        id = id,
        fullName = fullName,
        bankAccount = bankAccount,
        education = education,
        dob = dob,
        address = address,
        addressNo = addressNo,
        province = province,
        housing = houseType,
        isSubmit = submit
    )

    fun List<ProvinceEntity>.toProvModels(): List<Province> {
        return this.map { it.toModel() }
    }
    fun ProvinceResponse.toEntity() = ProvinceEntity(
        id = id.orEmpty(),
        name = name.orEmpty()
    )

    fun ProvinceResponse.toModel() = Province(
        id = id.orEmpty(),
        name = name.orEmpty()
    )

    fun ProvinceEntity.toModel() = Province(
        id = id,
        name = name
    )

}