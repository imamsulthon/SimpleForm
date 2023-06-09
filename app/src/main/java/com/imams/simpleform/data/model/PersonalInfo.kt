package com.imams.simpleform.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalInfo(
    var id: String,
    var fullName: String,
    var bankAccount: String,
    var education: String,
    var dob: String,
): Parcelable
