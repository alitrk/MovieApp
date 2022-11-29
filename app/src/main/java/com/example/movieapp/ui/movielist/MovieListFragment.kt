package com.example.movieapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(), ItemClickListener {

    private val viewModel: MovieListViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private var moviesFromRoom: ArrayList<Movie> = arrayListOf()
    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter(moviesFromRoom)}
    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.movieListFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListAdapter.listener = this
        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieListAdapter
        }
        errorMsg()
        subscribeToObservers()
        observeData()
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.viewState.collectLatest { movieViewState ->
                    binding.isLoading = movieViewState.isLoading
                    if (movieViewState.movieResponse != null) {
                        movieViewState.movieResponse.collectLatest {
                            movieListAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    private fun errorMsg(){
        viewLifecycleOwner.lifecycleScope.launch{
            movieListAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Error){
                    movieListAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                    val errorMessage = (loadStates.refresh as LoadState.Error).error.message
                    Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun searchOnClick(searchInput: String){
        if (searchInput!=""){
            viewModel.fetchPopularMovies(searchInput)
            observeData()
            errorMsg()
            viewModel.index = 1
        }
    }

    private fun subscribeToObservers() {
        viewModel.roomMovieList.observe(viewLifecycleOwner, Observer {
            movieListAdapter.updateRoom(it)
        })
    }


    override fun onButtonClickDelete(item: Movie) {
        viewModel.deleteMovie(item)
        viewModel.deleteMovieDetails(item)
    }

    override fun onButtonClickInsert(item: Movie) {
        viewModel.insertMovie(item)
        viewModel.insertMovieDetails(item)
    }

    fun fabOnClick(view: View){
        Navigation.navigate(view, R.id.action_navigation_movie_list_to_favouriteMoviesFragment)
    }



}
