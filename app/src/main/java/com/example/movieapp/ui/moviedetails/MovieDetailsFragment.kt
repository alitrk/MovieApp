package com.example.movieapp.ui.moviedetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var moviesFromRoom: ArrayList<Movie> = arrayListOf()
    private var isFavourite: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details, container, false)
        val bundle:MovieDetailsFragmentArgs by navArgs()
        val movieObject = bundle.movie
        if (movieObject != null) {
            viewModel.showMovieDetails(movieObject.id)
        }

        subscribeToObservers()
        if (movieObject != null) {
            roomMovieObserver(movieObject)
        }
        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.movieDetailsObject = it.data
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.constraintLayout.visibility = View.GONE
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.constraintLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun roomMovieObserver(movieObject:Movie) {
        viewModel.roomMovieListDetails.observe(viewLifecycleOwner, Observer {
            moviesFromRoom.addAll(it)
            for (i in moviesFromRoom){
                if (i.id == movieObject.id) {
                    isFavourite =  true
                    break
                }
            }
            requireActivity().addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.favourites_menu,menu)
                    val item = menu.getItem(0)
                    if (isFavourite){
                        item?.icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite)
                    }else{
                        item?.icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_not_favorite)
                    }
                }
                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId){
                        R.id.favourites_icon-> {
                            return true
                        }
                        else -> return false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}