package com.example.movieapp.ui.movielist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.MovieListItemBinding
import com.example.movieapp.databinding.MovieSeperatorBinding
import com.example.movieapp.util.navigate
import com.google.android.material.snackbar.Snackbar

class MovieListAdapter(private val movieList: ArrayList<Movie>) : PagingDataAdapter<UiModel, ViewHolder>(UIMODEL_COMPARATOR) {
    var listener : ItemClickListener ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == R.layout.movie_list_item) {
            val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MoviesViewHolder(binding)
        } else {
           val binding = MovieSeperatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SeparatorViewHolder(binding)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is UiModel.RepoItem -> R.layout.movie_list_item
            is UiModel.SeparatorItem -> R.layout.movie_seperator
            null -> throw  UnsupportedOperationException("Unknown view")
        }
    }

    class MoviesViewHolder(private val binding: MovieListItemBinding) : ViewHolder(binding.root) {
        val _binding = binding
        fun bind(item: UiModel.RepoItem) {
            binding.movie = item.movie
            binding.executePendingBindings()
        }
    }

    class SeparatorViewHolder(private val binding: MovieSeperatorBinding) : ViewHolder(binding.root) {
        fun bind(item: UiModel.SeparatorItem) {
            val pageNumText = item.pageNum
            binding.pageNumString = pageNumText.toString()
        }
    }


    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.RepoItem && newItem is UiModel.RepoItem &&
                        oldItem.movie.id == newItem.movie.id) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.pageNum == newItem.pageNum)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.RepoItem -> {
                    (holder as MoviesViewHolder).bind(uiModel)
                    val binding = holder._binding
                    if (movieList.contains(uiModel.movie)){
                        binding.ibMovieItemFav.setImageResource(R.drawable.ic_favorite)
                        binding.ibMovieItemFav.tag = "favourite"
                    }else{
                        binding.ibMovieItemFav.setImageResource(R.drawable.ic_not_favorite)
                        binding.ibMovieItemFav.tag = "notFavourite"
                    }
                    binding.recyclerViewRowMovie.setOnClickListener {
                        val navigate = MovieListFragmentDirections.actionNavigationMovieListToMovieDetailsFragment(uiModel.movie,uiModel.movie.title)
                        Navigation.navigate(it,navigate)
                    }

                    binding.ibMovieItemFav.setOnClickListener {
                        if(binding.ibMovieItemFav.tag =="notFavourite"){
                            listener?.onButtonClickInsert(uiModel.movie)
                            binding.ibMovieItemFav.setImageResource(R.drawable.ic_favorite)
                            binding.ibMovieItemFav.tag = "favourite"
                            Snackbar.make(it,"${uiModel.movie.title} has been added to your favourites", Snackbar.LENGTH_SHORT).show()
                        }else{
                            listener?.onButtonClickDelete(uiModel.movie)
                            binding.ibMovieItemFav.setImageResource(R.drawable.ic_not_favorite)
                            binding.ibMovieItemFav.tag = "notFavourite"
                            Snackbar.make(it,"${uiModel.movie.title} has been removed from your favourites", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel)
                else -> {}
            }

        }
    }

    fun updateRoom(movieListNew: List<Movie>){
        movieList.clear()
        movieList.addAll(movieListNew)
    }
}

interface ItemClickListener{
    fun onButtonClickDelete(item : Movie)
    fun onButtonClickInsert(item : Movie)

}
