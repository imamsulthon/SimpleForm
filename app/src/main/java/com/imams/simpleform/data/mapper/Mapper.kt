package com.imams.simpleform.data.mapper

import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.model.PersonalInfo
import com.imams.simpleform.data.model.Province
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
    )

    fun List<ProvinceEntity>.toModels(): List<Province> {
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