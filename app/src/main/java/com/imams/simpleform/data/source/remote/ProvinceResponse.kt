package com.imams.simpleform.data.source.remote

import com.google.gson.annotations.SerializedName

data class ProvinceResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val name: String?
)
