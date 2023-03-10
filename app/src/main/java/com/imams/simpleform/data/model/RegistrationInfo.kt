package com.imams.simpleform.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationInfo(
    var id: String,
    var fullName: String,
    var bankAccount: String,
    var education: String,
    var dob: String,
    var address: String,
    var houseType: String,
    var addressNo: String,
    var province: String,
    var isSubmit: Boolean = false
) : Parcelable