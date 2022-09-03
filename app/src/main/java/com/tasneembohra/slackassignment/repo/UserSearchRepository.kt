package com.tasneembohra.slackassignment.repo

import com.tasneembohra.slackassignment.network.api.UserSearchService
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.repo.model.UserSearchResult
import com.tasneembohra.slackassignment.repo.model.runResourceCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserSearchRepository {
    fun searchUser(term: String): Flow<Resource<Set<UserSearchResult>>>
}

class UserSearchRepositoryImpl(
    private val userSearchService: UserSearchService,
) : UserSearchRepository {
    override fun searchUser(term: String): Flow<Resource<Set<UserSearchResult>>> = flow {
        emit(Resource.Loading())
        val result = runResourceCatching(userSearchService.searchUsers(term)) { response ->
            response.users.map { UserSearchResult(it.username) }.toSet()
        }
        emit(result)
    }

}