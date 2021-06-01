package com.kajdasz.sample.omdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kajdasz.sample.omdb.repository.SearchRepoResult
import com.kajdasz.sample.omdb.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var lastSearchedTitle: String? = null

    val searchResult: LiveData<SearchRepoResult>
        get() = _searchResult

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _searchResult = MutableLiveData<SearchRepoResult>()

    private val _isLoading = MutableLiveData<Boolean>()

    fun fetchSearch(title: String? = lastSearchedTitle, page: Int = 1) {
        if (title == null) return

        lastSearchedTitle = title

        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = searchRepository.searchByTitle(title, page)
            _searchResult.postValue(response)
            _isLoading.postValue(false)
        }
    }
}
