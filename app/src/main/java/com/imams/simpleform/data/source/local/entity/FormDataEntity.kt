package com.imams.simpleform.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imams.simpleform.data.util.Constants


@Entity(tableName = Constants.DBValue.PersonalInfo)
data class FormDataEntity(

    @PrimaryKey
    @ColumnInfo("id") var id: Int,
    @ColumnInfo("full_name") var fullName: String,
    @ColumnInfo("bank_account") var bankAccount: String,
    @ColumnInfo("education") var education: String,
    @ColumnInfo("dob") var dob: String,
    @ColumnInfo("address") var address: String?,
    @ColumnInfo("address_no") var addressNo: String?,
    @ColumnInfo("province") var province: String?,
    @ColumnInfo("housing") var housing: String?,
)