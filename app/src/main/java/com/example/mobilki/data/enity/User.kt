package com.example.mobilki.data.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "phoneCode") val phoneCode: String?,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "pass") var pass: String?,
    @ColumnInfo(name = "isAdmin") var isAdmin: Boolean = false,
)
