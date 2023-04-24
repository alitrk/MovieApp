package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.*
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.repo.MovieRepository
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel(){

    var movieDetailsFromApi = MutableLiveData<Resource<MovieDetailsResponse>>()

    var movieDetailsFromRoom = MutableLiveData<MovieDetailsResponse>()



    fun showMovieDetailsFromRoom(id:String) {
        viewModelScope.launch {
            movieDetailsFromRoom.postValue(mrepo.observeMovieDetails(id))
        }
    }

    fun showMovieDetailsFromApi(id:String) {
        viewModelScope.launch {
            val response = mrepo.showMovieDetails(id)
            movieDetailsFromApi.value = response
        }
    }


}