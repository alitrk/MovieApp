package com.example.movieapp.ui.favouritemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentFavouriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteMoviesFragment : Fragment(), FavouriteMoviesAdapter.ItemClickListenerFavourites {

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding get() = _binding!!
    private val favouriteMoviesViewModel: FavouriteMoviesViewModel by viewModels()
    private val favouriteMoviesAdapter: FavouriteMoviesAdapter by lazy { FavouriteMoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        subscribeToObservers()
        _binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteMoviesAdapter.listener = this
        binding.rvFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteMoviesAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToObservers() {
        favouriteMoviesViewModel.roomMovieListDetailsFavourites.observe(viewLifecycleOwner, Observer {
            favouriteMoviesAdapter.submitList(it)
        })
    }

    override fun onButtonClickDelete(item: Movie) {
        favouriteMoviesViewModel.deleteMovieFavourites(item)
        favouriteMoviesViewModel.deleteMovieByID(item.id)
    }


}