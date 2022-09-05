package com.tasneembohra.slackassignment.repo.db.di

import androidx.room.Room
import com.tasneembohra.slackassignment.repo.db.SlackDatabase
import com.tasneembohra.slackassignment.repo.db.migration1_2
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SlackDatabase::class.java, "slack-database")
            .fallbackToDestructiveMigration()
            .createFromAsset("slack-database.db")
            .addMigrations(migration1_2)
            .build()
    }

    single {
        val database: SlackDatabase = get()
        database.userDao()
    }
}