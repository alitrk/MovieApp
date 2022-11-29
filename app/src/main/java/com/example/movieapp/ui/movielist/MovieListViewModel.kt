package com.example.movieapp.ui.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.repo.MovieRepository
import com.example.movieapp.error.ConsumableError
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MovieListViewState())
    val viewState: StateFlow<MovieListViewState> = _viewState.asStateFlow()
    var movieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    var index = 1
    val roomMovieList = mrepo.getMovies()
    private fun searchMovie(title: String): Flow<PagingData<UiModel>> =
        mrepo.searchMovie(title)
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

    fun fetchPopularMovies(title: String) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(
                    isLoading = true,
                )

            }
            val movieResponse = searchMovie(title)
            _viewState.update {
                it.copy(
                    isLoading = false,
                    movieResponse = movieResponse
                )
            }
        }
    }

    fun insertMovie(movie: Movie) = viewModelScope.launch {
        mrepo.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        mrepo.deleteMovie(movie)
    }

    fun insertMovieDetails(movie: Movie){
        viewModelScope.launch {
            val movieDetailsResponse = mrepo.showMovieDetails(movie.id).data!!
            mrepo.insertMovieDetails(movieDetailsResponse)
        }
    }

    fun deleteMovieDetails(movie: Movie){
        viewModelScope.launch {
            val movieDetailsResponse = mrepo.showMovieDetails(movie.id).data!!
            mrepo.deleteMovieDetails(movieDetailsResponse)
        }
    }

}




data class MovieListViewState(
    val isLoading: Boolean? = false,
    val movieResponse: Flow<PagingData<UiModel>>? = null,
    val consumableErrors: List<ConsumableError>? = null
)


sealed class UiModel {
    data class RepoItem(val movie: Movie, val index: Int) : UiModel()
    data class SeparatorItem(val pageNum: Int) : UiModel()
}
