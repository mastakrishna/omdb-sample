package com.kajdasz.sample.omdb.repository

import com.kajdasz.sample.omdb.model.MovieOnList

sealed class SearchRepoResult {
    data class Data(
        val moviesList: List<MovieOnList>,
        val hasAllResults: Boolean
    ) : SearchRepoResult()

    data class Error(
        val exception: Exception
    ) : SearchRepoResult()
}
