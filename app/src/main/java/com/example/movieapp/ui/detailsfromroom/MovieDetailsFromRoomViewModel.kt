package com.example.movieapp.ui.detailsfromroom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsFromRoomViewModel @Inject constructor(var mrepo: MovieRepository) :ViewModel() {

    var movieDetailResponse = MutableLiveData<MovieDetailsResponse>()

    fun showMovieDetails(id:String) {
        viewModelScope.launch {
            movieDetailResponse.value = mrepo.observeMovieDetails(id)
        }
    }
}