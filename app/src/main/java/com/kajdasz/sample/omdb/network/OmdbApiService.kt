package com.kajdasz.sample.omdb.network

import com.kajdasz.sample.omdb.BuildConfig
import com.kajdasz.sample.omdb.model.Movie
import com.kajdasz.sample.omdb.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchByTitle(
        @Query("s") title: String,
        @Query("page") page: Int = 1,
        @Query("apikey") key: String = BuildConfig.OMDB_KEY,
    ): SearchResult

    @GET("/")
    suspend fun fetchDetails(
        @Query("i") imdbID: String,
        @Query("apikey") key: String = BuildConfig.OMDB_KEY,
    ): Movie
}
