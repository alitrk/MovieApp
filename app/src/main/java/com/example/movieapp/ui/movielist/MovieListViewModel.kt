package com.example.movieapp.ui.movielist

import androidx.lifecycle.*
import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.data.repo.MovieRepository
import com.example.movieapp.error.ConsumableError
import com.example.movieapp.util.DummyData.movieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MovieListViewState())
    val viewState: StateFlow<MovieListViewState> = _viewState.asStateFlow()

    init {
        _viewState.update {
            it.copy(
                isLoading = true,
            )
        }
        //fetchPopularMovies("batman")
    }

    fun fetchPopularMovies(title: String) {
        viewModelScope.launch {
            delay(2000)
            val movieResponse = mrepo.searchMovie(title)
            if (movieResponse.response == "True"){
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        movieResponse = movieResponse
                    )
                }
            }else{
                addErrorToList(movieResponse.error)
            }

        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _viewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _viewState.value =
                viewState.value.copy(
                    consumableErrors = errorList,
                    isLoading = false
                )
        }
    }

    fun errorConsumed(errorId: Long) {
        _viewState.update { currentUiState ->
            val newConsumableError =
                currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError, isLoading = false)
        }
    }
}

data class MovieListViewState(
    val isLoading: Boolean? = false,
    val movieResponse: MoviesResponse? = null,
    val consumableErrors: List<ConsumableError>? = null
)