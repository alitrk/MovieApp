package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.repo.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(var mrepo: MovieRepository) : ViewModel(){


    fun showMovieDetails(id:String){
        CoroutineScope(Dispatchers.Main).launch {
            var movieDetails = mrepo.showMovieDetails(id)
        }
    }
}