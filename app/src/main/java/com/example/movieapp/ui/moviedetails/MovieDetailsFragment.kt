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
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var isFavourite: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        val bundle: MovieDetailsFragmentArgs by navArgs()

        val movieObjectId = bundle.id
        isFavourite = bundle.isFavourite

        if (bundle.isFavourite) {
            viewModel.showMovieDetailsFromRoom(movieObjectId)
            observeRoomData()
        } else {
            viewModel.showMovieDetailsFromApi(movieObjectId)
            observeApiData()
        }
        roomMovieObserver()
        return binding.root
    }

    private fun observeApiData() {
        viewModel.movieDetailsFromApi.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.movieDetailsObject = it.data
                    binding.progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.constraintLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.constraintLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeRoomData() {
        viewModel.movieDetailsFromRoom.observe(viewLifecycleOwner) {
            binding.movieDetailsObject = it
            binding.progressBar.visibility = View.GONE
            binding.constraintLayout.visibility = View.VISIBLE
        }
    }

    private fun roomMovieObserver() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favourites_menu, menu)
                val item = menu.getItem(0)
                if (isFavourite) {
                    item?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
                } else {
                    item?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.favourites_icon -> {
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}