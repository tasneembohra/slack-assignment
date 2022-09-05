package com.tasneembohra.slackassignment.repo

import com.tasneembohra.slackassignment.network.api.UserSearchService
import com.tasneembohra.slackassignment.repo.db.dao.UserSearchDao
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword
import com.tasneembohra.slackassignment.repo.model.ErrorCode
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.repo.model.User
import com.tasneembohra.slackassignment.repo.model.UserSearchResultMapper
import com.tasneembohra.slackassignment.repo.model.runResourceCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

interface UserSearchRepository {
    fun searchUser(term: String): Flow<Resource<Set<User>>>
}

class UserSearchRepositoryImpl(
    private val userSearchService: UserSearchService,
    private val userSearchDao: UserSearchDao,
) : UserSearchRepository {

    override fun searchUser(term: String): Flow<Resource<Set<User>>> = flow {
        emit(Resource.Loading())

        // Check if the username is matched with the denied list of search then return error with Not Found status
        if (userSearchDao.getDeniedKeyword(term)?.isNotEmpty() == true) {
            emit(Resource.Error(errorCode = ErrorCode.NOT_FOUND))
            return@flow
        }

        val result = runResourceCatching(userSearchService.searchUsers(term)) { response ->
            response.users.map(UserSearchResultMapper::map).toSet()
        }.also {
            when {
                it.errorCode == ErrorCode.NOT_FOUND -> {
                    // If api returns not found error, then update denied keywords table
                    userSearchDao.addDeniedKeyword(DeniedKeyword(term))
                }
                it is Resource.Success -> {
                    // Add users to User table for offline search
                    userSearchDao.addUser(it.data)
                }
            }
        }
        emit(result)
    }
}