package com.tasneembohra.slackassignment.repo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Models users returned by the API.
 */
@Entity(tableName = "user_detail")
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "user_name") val username: String,
)