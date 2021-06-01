package com.kajdasz.sample.omdb.ui.search.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchScrollListener(
    private val fetchMoreAction: (Int) -> Unit,
    private val isLoading: () -> Boolean,
    private val hasAllResults: () -> Boolean,
) : RecyclerView.OnScrollListener() {
    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isLoading() || hasAllResults()) return

        (recyclerView.layoutManager as? GridLayoutManager)?.let { manager ->
            val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
            val visibleItemCount = manager.childCount
            val totalItemCount = manager.itemCount

            if (visibleItemCount + lastVisibleItemPosition + manager.spanCount >= totalItemCount) {
                fetchMoreAction(++currentPage)
            }
        }
    }

    fun resetPage() {
        currentPage = 1
    }
}
