package com.imams.simpleform.data.source.local.dao

import androidx.room.*
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDao {

    @Query("SELECT * FROM ${Constants.DBValue.PersonalInfo}")
    fun getAllUser(): Flow<List<FormDataEntity>>

    @Query("SELECT * FROM ${Constants.DBValue.PersonalInfo} WHERE id =:id")
    fun getUserById(id: String): Flow<FormDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(dataEntity: FormDataEntity)

    @Delete
    suspend fun delete(data: FormDataEntity)

}