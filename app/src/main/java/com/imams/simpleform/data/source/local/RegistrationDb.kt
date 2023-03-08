package com.imams.simpleform.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imams.simpleform.data.source.local.entity.FormDataEntity


@Database(
    entities = [
        FormDataEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class RegistrationDb: RoomDatabase() {

    abstract fun registrationDao(): RegistrationDao

}