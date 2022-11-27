package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.repo.MovieRepository
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel(){

    var movieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()


    fun showMovieDetails(id:String) {
        viewModelScope.launch {
            val response = mrepo.showMovieDetails(id)
            movieDetails.value = response
        }
    }
}