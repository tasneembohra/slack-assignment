package com.tasneembohra.slackassignment.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasneembohra.slackassignment.repo.db.dao.UserSearchDao
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword
import com.tasneembohra.slackassignment.repo.model.User

@Database(entities = [User::class, DeniedKeyword::class], version = 1)
abstract class SlackDatabase: RoomDatabase() {
    abstract fun userDao(): UserSearchDao
}

/*
 * Function to migrate all denylist to prepopulate database
* fun readDataFromFile(filename: String): List<String> {
        val inputStream = javaClass.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("No such resource: $filename")
        inputStream.use { stream ->
            val list = mutableListOf<String>()
            val reader = BufferedReader(InputStreamReader(stream))

            var str: String? = reader.readLine()
            while (str != null) {
                list += str
                str = reader.readLine()
            }
            return list
        }
    }*/