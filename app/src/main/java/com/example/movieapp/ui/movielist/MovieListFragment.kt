package com.example.movieapp.ui.movielist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter() }
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
        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieListAdapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.viewState.collectLatest { movieViewState ->
                    movieViewState.consumableErrors?.let { consumableError ->
                        consumableError.firstOrNull()?.let { error ->
                            Toast.makeText(context, error.exception, Toast.LENGTH_SHORT).show()
                        }
                    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:MovieListViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun searchOnClick(searchInput: String){
        if (searchInput!=""){
            viewModel.fetchPopularMovies(searchInput)
            observeData()
        }

    }

}
