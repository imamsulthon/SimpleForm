package com.imams.simpleform.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressInfo(
    var id: String,
    var address: String,
    var houseType: String,
    var addressNo: String,
    var province: String,
): Parcelable
