package com.imams.simpleform.data.mapper

import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.model.PersonalInfo

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
}