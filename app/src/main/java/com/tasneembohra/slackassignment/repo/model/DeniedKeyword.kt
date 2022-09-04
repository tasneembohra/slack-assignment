package com.tasneembohra.slackassignment.repo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "denied_keywords")
data class DeniedKeyword(@PrimaryKey val keyword: String)