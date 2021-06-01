package com.kajdasz.sample.omdb.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.kajdasz.sample.omdb.R
import com.kajdasz.sample.omdb.repository.SearchRepoResult
import com.kajdasz.sample.omdb.ui.search.list.MovieListAdapter
import com.kajdasz.sample.omdb.ui.search.list.SearchScrollListener
import com.kajdasz.sample.omdb.ui.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var searchBox: EditText
    private lateinit var isLoadingView: View
    private lateinit var errorTextView: TextView
    private lateinit var movieListView: RecyclerView
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var scrollListener: SearchScrollListener

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLoadingView = view.findViewById(R.id.loading)
        errorTextView = view.findViewById(R.id.errorText)

        searchBox = view.findViewById(R.id.searchBox)
        searchBox.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (actionId == EditorInfo.IME_ACTION_UNSPECIFIED && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN)
            ) {
                searchBox.hideKeyboard()
                scrollListener.resetPage()
                searchViewModel.fetchSearch(title = searchBox.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        movieListView = view.findViewById(R.id.movieList)
        movieListAdapter = MovieListAdapter()
        movieListView.adapter = movieListAdapter
        movieListAdapter.setOnMovieClicked { imdbId ->
            // TODO: Show detail fragment
        }

        scrollListener = SearchScrollListener(
            fetchMoreAction = { page ->
                searchViewModel.fetchSearch(page = page)
            },
            isLoading = {
                searchViewModel.isLoading.value == true
            },
            hasAllResults = {
                (searchViewModel.searchResult.value as? SearchRepoResult.Data)?.hasAllResults == true
            }
        )

        movieListView.addOnScrollListener(scrollListener)

        searchViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SearchRepoResult.Error -> {
                    errorTextView.isVisible = true
                    movieListView.isVisible = false
                    errorTextView.text = getString(R.string.search_error, result.exception.message)
                }
                is SearchRepoResult.Data -> {
                    errorTextView.isVisible = false
                    movieListView.isVisible = true
                    movieListAdapter.submitList(result.moviesList)
                }
            }
        }

        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            isLoadingView.isInvisible = !isLoading
        }
    }

}
