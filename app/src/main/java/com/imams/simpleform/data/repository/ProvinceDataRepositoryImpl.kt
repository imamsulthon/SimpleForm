package com.imams.simpleform.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.imams.simpleform.data.mapper.Mapper.toEntity
import com.imams.simpleform.data.mapper.Mapper.toModel
import com.imams.simpleform.data.mapper.Mapper.toModels
import com.imams.simpleform.data.model.Province
import com.imams.simpleform.data.source.local.dao.ProvinceDao
import com.imams.simpleform.data.source.remote.ProvinceApi
import com.imams.simpleform.data.source.remote.ProvinceResponse
import com.imams.simpleform.data.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProvinceDataRepositoryImpl @Inject constructor(
    private val provinceApi: ProvinceApi,
    private val provinceDao: ProvinceDao,
): ProvinceDataRepository {

    override suspend fun getProvinces(): Flow<List<Province>> {
        return flow {
            try {
                val response = provinceApi.getProvinceData()
                val entities = response.map { it.toEntity() }
                provinceDao.addProvinces(entities)
                provinceDao.getAllProvince().collectLatest {
                    emit(it.map { e -> e.toModel() })
                }
            } catch (e: Exception) {
                printLog("catch 1 ${e.message}")
                emit(defaultProvince().map { it.toModel() })
            }
        }.flowOn(Dispatchers.IO).catch {
            printLog("catch 2 ${it.message}")
            emit(defaultProvince().map { i -> i.toModel() })
        }
    }

    override suspend fun getProvinceRemote(): Flow<List<Province>> {
        return flow {
            try {
                val response = provinceApi.getProvinceData()
                emit(response.map { it.toModel() })
            } catch (e: Exception) {
                emit(defaultProvince().map { it.toModel() })
            }
        }
    }

    override suspend fun getProvincesLocal(): Flow<List<Province>> {
        return provinceDao.getAllProvince().map {it.toModels() }
    }

    private fun printLog(msg: String, tag: String? = "ProvinceRepo") {
        println("$tag: msg -> $msg")
    }

    private fun defaultProvince(): List<ProvinceResponse> {
        val type = object : TypeToken<List<ProvinceResponse>>() {}.type
        val list = Gson().fromJson<List<ProvinceResponse>>(Constants.defaultProvince, type)
        return list ?: listOf()
    }
}