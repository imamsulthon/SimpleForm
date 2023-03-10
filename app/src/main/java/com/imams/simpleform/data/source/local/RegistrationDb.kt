package com.imams.simpleform.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imams.simpleform.data.source.local.dao.ProvinceDao
import com.imams.simpleform.data.source.local.dao.RegistrationDao
import com.imams.simpleform.data.source.local.entity.FormDataEntity
import com.imams.simpleform.data.source.local.entity.ProvinceEntity


@Database(
    entities = [
        FormDataEntity::class,
        ProvinceEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class RegistrationDb: RoomDatabase() {

    abstract fun registrationDao(): RegistrationDao

    abstract fun provinceDao(): ProvinceDao

}