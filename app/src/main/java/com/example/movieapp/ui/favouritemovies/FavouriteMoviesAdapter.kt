package com.example.movieapp.ui.favouritemovies

import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FavouritesListItemBinding
import com.example.movieapp.ui.movielist.ItemClickListener
import com.google.android.material.snackbar.Snackbar


class FavouriteMoviesAdapter : ListAdapter<Movie, FavouriteMoviesAdapter.FavouritesMoviesViewHolder>(MovieListUtil()) {
    var listener : ItemClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesMoviesViewHolder {
        val binding = FavouritesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouritesMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouritesMoviesViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            val binding = holder._binding
            binding.ibMovieItemFavFavourites.setOnClickListener {
                val snackbar = Snackbar.make(it,"Are you sure you want to remove ${movie.title} from your favourites list?", Snackbar.LENGTH_LONG)
                    .setAction("Yes"){
                        listener?.onButtonClickDelete(movie)
                    }
                snackbar.show()
            }

            holder.bind(it)
        }
    }

    class FavouritesMoviesViewHolder(private val binding: FavouritesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val _binding = binding
        fun bind(item: Movie) {
            binding.movieFavourites = item
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