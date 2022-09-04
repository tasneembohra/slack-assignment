package com.tasneembohra.slackassignment.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasneembohra.slackassignment.network.model.User
import com.tasneembohra.slackassignment.repo.db.dao.UserSearchDao
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword

@Database(entities = [User::class, DeniedKeyword::class], version = 1)
abstract class SlackDatabase: RoomDatabase() {
    abstract fun userDao(): UserSearchDao
}