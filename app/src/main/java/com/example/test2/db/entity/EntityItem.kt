package com.example.shift_tt.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "items")
data class EntityItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,

    @ColumnInfo(name = "picture")
    var picture: String,

    @ColumnInfo(name = "latitude")
    var latitude: String,
    @ColumnInfo(name = "longitude")
    var longitude: String,

    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "state")
    var state: String,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String,
    )