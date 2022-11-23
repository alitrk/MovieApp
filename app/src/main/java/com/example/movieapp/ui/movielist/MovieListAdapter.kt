package com.example.movieapp.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.MovieListItemBinding

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MoviesViewHolder>(MovieListUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MoviesViewHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()
        }
    }

    class MovieListUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.posterPath == newItem.posterPath
                    && oldItem.year == newItem.year
        }
    }
}
