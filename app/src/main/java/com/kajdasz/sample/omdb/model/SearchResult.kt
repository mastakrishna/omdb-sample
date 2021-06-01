package com.kajdasz.sample.omdb.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    val moviesList: List<MovieOnList>,

    val totalResults: Int,

    @SerializedName("Response")
    val response: String,
)
