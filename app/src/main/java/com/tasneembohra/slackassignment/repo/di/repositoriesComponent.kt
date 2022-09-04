package com.tasneembohra.slackassignment.repo.di

import com.tasneembohra.slackassignment.repo.UserSearchRepository
import com.tasneembohra.slackassignment.repo.UserSearchRepositoryImpl
import com.tasneembohra.slackassignment.repo.db.databaseModule
import org.koin.dsl.module

val repositoriesModules = module {
    single<UserSearchRepository> {
        UserSearchRepositoryImpl(
            userSearchService = get(),
            userSearchDao = get(),
        )
    }
}
