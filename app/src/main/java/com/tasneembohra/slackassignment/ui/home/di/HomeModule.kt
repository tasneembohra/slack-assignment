package com.tasneembohra.slackassignment.ui.home.di

import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
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

    factory {
        DividerItemDecoration(
            get(),
            LinearLayout.VERTICAL,
        )
    }

}