package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabase
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModelFactory

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory

    private lateinit var movieDatabaseDao: MovieDatabaseDao

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie

        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getInstance(application).movieDatabaseDao

        viewModelFactory = MovieDetailViewModelFactory(movieDatabaseDao, application, movie)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        binding.movie = movie
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = "Movie DB App"
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = title
        // DO NOT add the back button to the bar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }
        binding.reviewsButton.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieVideosAndReviews(movie))
        }

    }
}