package com.imams.simpleform.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imams.simpleform.data.source.local.entity.ProvinceEntity
import com.imams.simpleform.data.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ProvinceDao {

    @Query("SELECT * FROM ${Constants.DBValue.ProvinceData}")
    fun getAllProvince(): Flow<List<ProvinceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProvinces(data: List<ProvinceEntity>)

}