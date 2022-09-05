package com.tasneembohra.slackassignment.ui.home.di

import com.tasneembohra.slackassignment.ui.home.HomeViewModel
import com.tasneembohra.slackassignment.util.base.adapters.BaseListAdapter
import com.tasneembohra.slackassignment.util.base.viewholderfactory.BaseViewHolderFactory
import org.koin.dsl.module

val homeModule = module {

    factory {
        HomeViewModel(
            userSearchRepository = get(),
        )
    }
    factory { BaseViewHolderFactory() }

    factory {
        BaseListAdapter(viewHolderFactory = get())
    }

}