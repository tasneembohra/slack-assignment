package com.tasneembohra.slackassignment.repo.db

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SlackDatabase::class.java, "slack-database"
        ).build()
    }

    single {
        val database: SlackDatabase = get()
        database.userDao()
    }
}