package com.example.movieapp.ui.detailsfromroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.FragmentMovieDetailsFromRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFromRoomFragment : Fragment() {

    private var _binding: FragmentMovieDetailsFromRoomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsFromRoomViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieDetailsFromRoomBinding.inflate(inflater, container, false)
        val bundle: MovieDetailsFromRoomFragmentArgs by navArgs()
        val bundleId = bundle.id

        if (bundleId != null) {
            viewModel.showMovieDetails(bundleId)
        }

        subscribeToObservers()
        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.movieDetailResponse.observe(viewLifecycleOwner, Observer {
            binding.movieDetailsObjectRoom = it
            binding.progressBarRoomDetails.visibility = View.GONE
            binding.constraintLayoutRoomDetails.visibility = View.VISIBLE
        })
    }


}