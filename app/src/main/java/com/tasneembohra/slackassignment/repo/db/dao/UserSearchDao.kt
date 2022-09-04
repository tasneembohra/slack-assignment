package com.tasneembohra.slackassignment.repo.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword

interface UserSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeniedKeyword(keyword: DeniedKeyword)

    @Query("SELECT :keyword FROM denied_keywords WHERE keyword LIKE :keyword || '%' LIMIT 1")
    fun getDeniedKeyword(keyword: String): String?

}