package com.tasneembohra.slackassignment.ui.home

import androidx.lifecycle.ViewModel
import com.tasneembohra.slackassignment.repo.UserSearchRepository
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.model.UserUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class HomeViewModel(
    userSearchRepository: UserSearchRepository
) : ViewModel() {

    private val userSearchKeyword = MutableStateFlow<String?>(null)

    val data: Flow<Resource<List<UserUi>>> = userSearchKeyword.flatMapLatest {
        if (it == null) return@flatMapLatest emptyFlow()
        userSearchRepository.searchUser(it)
    }.mapLatest { resource ->
        resource.map { result ->
            result.map { UserUi(it.username) }
        }
    }
}