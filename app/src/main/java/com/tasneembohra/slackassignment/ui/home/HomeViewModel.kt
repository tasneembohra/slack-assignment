package com.tasneembohra.slackassignment.ui.home

import androidx.lifecycle.ViewModel
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.repo.UserSearchRepository
import com.tasneembohra.slackassignment.repo.model.ErrorCode
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.model.UserSearchUiMapper
import com.tasneembohra.slackassignment.util.model.BaseItemUi
import com.tasneembohra.slackassignment.util.model.ErrorUi
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

    val data: Flow<Resource<List<BaseItemUi>>> = userSearchKeyword.flatMapLatest {
        if (it.isNullOrBlank()) return@flatMapLatest emptyFlow()
        userSearchRepository.searchUser(it)
    }.mapLatest { resource ->
        resource.map {
            val list = mutableListOf<BaseItemUi>()
            when(resource.errorCode) {
                ErrorCode.NO_INTERNET -> list += ErrorUi(messageId = R.string.no_internet_error_message)
                ErrorCode.NOT_FOUND -> list += ErrorUi(messageId = R.string.user_not_found_error_message)
                ErrorCode.SOMETHING_ELSE -> list += ErrorUi(messageId = R.string.generic_error_message)
                else -> Unit
            }
            it?.map(UserSearchUiMapper::map)?.let { list += it }
            list
        }
    }.flowOn(Dispatchers.IO)

    fun search(searchQuery: String?) {
        userSearchKeyword.value = searchQuery
    }
}