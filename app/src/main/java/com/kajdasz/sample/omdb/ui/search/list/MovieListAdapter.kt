package com.kajdasz.sample.omdb.ui.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kajdasz.sample.omdb.databinding.MovieOnListBinding
import com.kajdasz.sample.omdb.model.MovieOnList

class MovieListAdapter : ListAdapter<MovieOnList, MovieListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieOnList>() {
            override fun areItemsTheSame(oldItem: MovieOnList, newItem: MovieOnList) =
                oldItem.imdbID == newItem.imdbID

            override fun areContentsTheSame(oldItem: MovieOnList, newItem: MovieOnList) =
                oldItem == newItem
        }
    }

    private lateinit var onMovieClicked: ((imdbID: String) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieOnListBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.binding.root.setOnClickListener {
            onMovieClicked(movie.imdbID)
        }
    }

    fun setOnMovieClicked(event: ((imdbID: String) -> Unit)) {
        onMovieClicked = event
    }

    class MovieViewHolder(val binding: MovieOnListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieOnList) {
            binding.movieOnList = movie
        }
    }
}
