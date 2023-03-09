package com.imams.simpleform.data.source.remote

import retrofit2.http.GET

interface ProvinceApi {

    @GET("data-indonesia/provinsi.json")
    suspend fun getProvinceData(): List<ProvinceResponse>

}