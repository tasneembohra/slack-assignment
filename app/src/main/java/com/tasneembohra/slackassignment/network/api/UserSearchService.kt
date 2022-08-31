package com.tasneembohra.slackassignment.network.api

import com.tasneembohra.slackassignment.network.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchService {
  /**
   * Search query. Returns the API response.
   */
  @GET("search")
  suspend fun searchUsers(@Query("query") query: String): UserSearchResponse
}