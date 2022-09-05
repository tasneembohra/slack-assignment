package com.tasneembohra.slackassignment.repo.db.di

import androidx.room.Room
import com.tasneembohra.slackassignment.repo.db.SlackDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SlackDatabase::class.java, "slack-database"
        ).createFromAsset("slack-database.db")
            .build()

    }

    single {
        val database: SlackDatabase = get()
        database.userDao()
    }
}