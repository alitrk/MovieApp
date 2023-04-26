package com.example.movieapp.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private var mrepo: MovieRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MovieListViewState())
    val viewState: StateFlow<MovieListViewState> = _viewState.asStateFlow()

    var index = 1
    val roomMovieList = mrepo.getMovies()
    fun fetchPopularMovies(title: String) {
        viewModelScope.launch {
            val movieResponse = mrepo.searchMovie(title)
                .map { pagingData -> pagingData.map { UiModel.RepoItem(it, index++) } }
                .map {
                    it.insertSeparators { before, after ->
                        if (after == null) {
                            // we're at the end of the list
                            return@insertSeparators null
                        }
                        if (before == null) {
                            // we're at the beginning of the list
                            return@insertSeparators UiModel.SeparatorItem(1)
                        }
                        if (before.index % 10 == 0) {
                            return@insertSeparators UiModel.SeparatorItem((before.index / 10) + 1)

                        } else {
                            null
                        }
                    }
                }
                .cachedIn(viewModelScope)
            _viewState.value = _viewState.value.copy(movieResponse = movieResponse)
        }
    }

    fun insertMovie(movie: Movie) = viewModelScope.launch {
        mrepo.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        mrepo.deleteMovie(movie)
    }

    fun insertMovieDetails(movie: Movie) {
        viewModelScope.launch {
            val movieDetailsResponse = mrepo.showMovieDetails(movie.id).data
            if (movieDetailsResponse != null) {
                mrepo.insertMovieDetails(movieDetailsResponse)
            }
        }
    }


    fun deleteMovieDetails(movie: Movie) {
        viewModelScope.launch {
            val movieDetailsResponse = mrepo.showMovieDetails(movie.id).data
            if (movieDetailsResponse != null) {
                mrepo.deleteMovieDetails(movieDetailsResponse)
            }
        }
    }

}


data class MovieListViewState(
    val movieResponse: Flow<PagingData<UiModel>>? = null,
)


sealed class UiModel {
    data class RepoItem(val movie: Movie, val index: Int) : UiModel()
    data class SeparatorItem(val pageNum: Int) : UiModel()
}
