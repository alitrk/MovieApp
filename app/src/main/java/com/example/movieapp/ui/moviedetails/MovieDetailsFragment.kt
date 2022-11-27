package com.example.movieapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details, container, false)
        val bundle:MovieDetailsFragmentArgs by navArgs()
        val idDetails = bundle.id
        viewModel.showMovieDetails(idDetails)
        subscribeToObservers()

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}