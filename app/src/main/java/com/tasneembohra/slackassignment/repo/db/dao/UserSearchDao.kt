package com.tasneembohra.slackassignment.repo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword
import com.tasneembohra.slackassignment.repo.model.User

@Dao
interface UserSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeniedKeyword(keyword: DeniedKeyword)

    @Query("SELECT :keyword FROM denied_keywords WHERE keyword LIKE :keyword || '%' LIMIT 1")
    fun getDeniedKeyword(keyword: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(users: Set<User>)

}