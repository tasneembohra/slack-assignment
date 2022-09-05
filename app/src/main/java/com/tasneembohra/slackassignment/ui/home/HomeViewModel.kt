package com.tasneembohra.slackassignment.ui.home

import androidx.lifecycle.ViewModel
import com.tasneembohra.slackassignment.repo.UserSearchRepository
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.model.UserSearchUiMapper
import com.tasneembohra.slackassignment.util.model.AvatarUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

class HomeViewModel(
    userSearchRepository: UserSearchRepository
) : ViewModel() {
    private val userSearchKeyword = MutableStateFlow<String?>(null)

    val data: Flow<Resource<List<AvatarUi>>> = userSearchKeyword.flatMapLatest {
        if (it == null) return@flatMapLatest emptyFlow()
        userSearchRepository.searchUser(it)
    }.mapLatest { resource ->
        resource.map { it.map(UserSearchUiMapper::map) }
    }.flowOn(Dispatchers.IO)

    fun search(searchQuery: String?) {
        userSearchKeyword.value = searchQuery
    }
}