package com.kajdasz.sample.omdb.repository

import com.kajdasz.sample.omdb.model.MovieOnList
import com.kajdasz.sample.omdb.network.OmdbApiService
import javax.inject.Inject

interface SearchRepository {
    suspend fun searchByTitle(title: String, page: Int): SearchRepoResult
}

class SearchRepositoryImpl @Inject constructor(
    private val omdbApiService: OmdbApiService,
) : SearchRepository {
    
    private val searchMoviesList = mutableListOf<MovieOnList>()

    override suspend fun searchByTitle(title: String, page: Int): SearchRepoResult =
        try {
            val searchResult = omdbApiService.searchByTitle(title, page)
            if (page == 1) {
                searchMoviesList.clear()
            }
            searchMoviesList.addAll(searchResult.moviesList)

            SearchRepoResult.Data(
                moviesList = searchMoviesList.toList(),
                hasAllResults = searchMoviesList.size >= searchResult.totalResults
            )
        } catch (e: Exception) {
            SearchRepoResult.Error(e)
        }
}
