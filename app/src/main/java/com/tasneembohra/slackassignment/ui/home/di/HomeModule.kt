package com.tasneembohra.slackassignment.ui.home.di

import com.tasneembohra.slackassignment.ui.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {

    factory {
        HomeViewModel(
            userSearchRepository = get(),
        )
    }

}