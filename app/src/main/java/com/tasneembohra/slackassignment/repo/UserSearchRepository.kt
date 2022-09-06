package com.tasneembohra.slackassignment.repo

import com.tasneembohra.slackassignment.network.api.UserSearchService
import com.tasneembohra.slackassignment.repo.db.dao.UserSearchDao
import com.tasneembohra.slackassignment.repo.model.DeniedKeyword
import com.tasneembohra.slackassignment.repo.model.ErrorCode.NOT_FOUND
import com.tasneembohra.slackassignment.repo.model.ErrorCode.NO_INTERNET
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.repo.model.User
import com.tasneembohra.slackassignment.repo.model.UserSearchResultMapper
import com.tasneembohra.slackassignment.repo.model.runResourceCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
            emit(Resource.Error(errorCode = NOT_FOUND))
            return@flow
        }

        var result = runResourceCatching {
            val response = userSearchService.searchUsers(term)
            if (!response.ok && response.error == "Not found") throw UserNotFoundException
            response.users.map(UserSearchResultMapper::map).toSet().also {
                // Add users to User table for offline search
                userSearchDao.addUser(it)
            }
        }

        when {
            result is Resource.Error && result.exception == UserNotFoundException -> {
                // If api returns not found error, then update denied keywords table
                userSearchDao.addDeniedKeyword(DeniedKeyword(term))
                result = Resource.Error(errorCode = NOT_FOUND)
            }
            result is Resource.Error && result.errorCode == NO_INTERNET -> {
                // When there is no internet connection then search user in database
                result = Resource.Error(
                    errorCode = NO_INTERNET,
                    data = userSearchDao.searchUsers(term).toSet()
                )
            }

        }

        emit(result)
    }
}

object UserNotFoundException : RuntimeException()