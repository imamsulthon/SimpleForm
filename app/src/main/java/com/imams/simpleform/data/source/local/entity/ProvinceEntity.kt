package com.imams.simpleform.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imams.simpleform.data.util.Constants

@Entity(tableName = Constants.DBValue.ProvinceData)
data class ProvinceEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("name") val name: String
)
