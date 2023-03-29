package com.imams.simpleform.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toProvModels
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.data.source.local.dao.ProvinceDao
import com.imams.simpleform.data.source.remote.ProvinceApi
import com.imams.simpleform.data.source.remote.ProvinceResponse
import com.imams.simpleform.data.util.Constants
import com.imams.simpleform.data.util.DataExt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProvinceDataRepositoryImpl @Inject constructor(
    private val provinceApi: ProvinceApi,
    private val provinceDao: ProvinceDao,
    private val context: Context,
): ProvinceDataRepository {

    override suspend fun getProvinces(): Flow<List<Province>> {
        return channelFlow {
            try {
//                val response = provinceApi.getProvinceData()
                val response = defaultProvince()
                val entities = response.map { it.toEntity() }
                provinceDao.addProvinces(entities)
                provinceDao.getAllProvince().collectLatest {
                    send(it.map { e -> e.toModel() })
                }
            } catch (e: Exception) {
                send(alternateProvince().map { it.toModel() })
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(alternateProvince().map { i -> i.toModel() })
        }
    }

    override suspend fun getProvinceRemote(): Flow<List<Province>> {
        return flow {
            try {
                val response = provinceApi.getProvinceData()
                emit(response.map { it.toModel() })
            } catch (e: Exception) {
                emit(alternateProvince().map { it.toModel() })
            }
        }
    }

    override suspend fun getProvincesLocal(): Flow<List<Province>> {
        return provinceDao.getAllProvince().map {it.toProvModels() }
    }

    private fun defaultProvince(): List<ProvinceResponse> {
        val json = DataExt.getJSONFile(context, "provinces.json")
        val type = object : TypeToken<List<ProvinceResponse>>() {}.type
        val list = Gson().fromJson<List<ProvinceResponse>>(json, type)
        return list ?: listOf()
    }

    private fun alternateProvince(): List<ProvinceResponse> {
        val type = object : TypeToken<List<ProvinceResponse>>() {}.type
        val list = Gson().fromJson<List<ProvinceResponse>>(Constants.defaultProvince, type)
        return list ?: listOf()
    }
}