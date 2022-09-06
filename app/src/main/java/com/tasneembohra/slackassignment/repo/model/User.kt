package com.tasneembohra.slackassignment.repo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Models users returned by the API.
 */
@Entity(tableName = "user_detail")
data class User(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "user_name") val username: String,
    @ColumnInfo(name = "display_name") val displayName: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
)