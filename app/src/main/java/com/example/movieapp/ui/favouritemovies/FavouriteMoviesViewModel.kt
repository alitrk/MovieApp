package com.example.movieapp.ui.favouritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel() {
    val roomMovieListDetailsFavourites = mrepo.getMovies()


    fun deleteMovieFavourites(movie: Movie) = viewModelScope.launch {
        mrepo.deleteMovie(movie)
    }

    fun deleteMovieByID(id: String) = viewModelScope.launch {
        mrepo.deleteMovieByID(id)
    }

}