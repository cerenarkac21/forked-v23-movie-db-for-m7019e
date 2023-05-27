package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.ltu.m7019e.v23.themoviedb.adapter.MovieReviewListAdapter
import com.ltu.m7019e.v23.themoviedb.adapter.MovieReviewListClickListener
import com.ltu.m7019e.v23.themoviedb.adapter.MovieVideoListAdapter
import com.ltu.m7019e.v23.themoviedb.adapter.MovieVideoListClickListener
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieVideosAndReviewsBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.NetworkStatusCallback
import com.ltu.m7019e.v23.themoviedb.utils.CustomLinearLayoutManager
import com.ltu.m7019e.v23.themoviedb.viewmodel.*


class MovieVideosAndReviewsFragment : Fragment() {

    private lateinit var reviewsViewModelFactory: MovieReviewListViewModelFactory
    private lateinit var reviewsViewModel: MovieReviewListViewModel

    private lateinit var videosViewModel: MovieVideoListViewModel
    private lateinit var videosViewModelFactory: MovieVideoListViewModelFactory

    private lateinit var networkStatusCallback: NetworkStatusCallback

    private var _binding: FragmentMovieVideosAndReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // make the system know that the fragment
        // has an options menu which will be set in the OnViewCreated() function later.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieVideosAndReviewsBinding.inflate(inflater)

        movie = MovieVideosAndReviewsFragmentArgs.fromBundle(requireArguments()).movie
        val container = TheMovieDataBase.getContainer(requireContext())
        val movieRepository = container.moviesRepository
        val application = requireNotNull(this.activity).application

        reviewsViewModelFactory =
            MovieReviewListViewModelFactory(movie.id, application, movieRepository)
        reviewsViewModel = ViewModelProvider(
            this,
            reviewsViewModelFactory
        ).get(MovieReviewListViewModel::class.java)

        videosViewModelFactory =
            MovieVideoListViewModelFactory(movie.id, application, movieRepository)
        videosViewModel =
            ViewModelProvider(this, videosViewModelFactory).get(MovieVideoListViewModel::class.java)

        // Initialize the network status callback
        networkStatusCallback = NetworkStatusCallback(application, movieRepository)
        networkStatusCallback.registerNetworkCallback()


        binding.reviewsRecyclerView.layoutManager = CustomLinearLayoutManager(requireContext())

        val movieReviewListAdapter = MovieReviewListAdapter(MovieReviewListClickListener { review ->
            reviewsViewModel.onMovieReviewListItemClicked(review)
        })

        val movieVideoListAdapter = MovieVideoListAdapter(MovieVideoListClickListener { video ->
            videosViewModel.onMovieVideoListItemClicked(video)
        })

        val reviewSnapHelper = PagerSnapHelper()
        reviewSnapHelper.attachToRecyclerView(binding.reviewsRecyclerView)

        val videoSnapHelper = PagerSnapHelper()
        videoSnapHelper.attachToRecyclerView(binding.videosRecyclerView)

        binding.reviewsRecyclerView.adapter = movieReviewListAdapter

        reviewsViewModel.movieReviewList.observe(viewLifecycleOwner) { movieReviewList ->
            movieReviewList?.let {
                movieReviewListAdapter.submitList(movieReviewList)
            }
        }

        binding.videosRecyclerView.adapter = movieVideoListAdapter

        videosViewModel.movieVideoList.observe(viewLifecycleOwner) { movieVideoList ->
            movieVideoList?.let {
                movieVideoListAdapter.submitList(movieVideoList)
            }
        }

        reviewsViewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImageReviews.visibility = View.VISIBLE
                        binding.statusImageReviews.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImageReviews.visibility = View.VISIBLE
                        binding.statusImageReviews.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImageReviews.visibility = View.GONE
                    }
                }
            }
        }

        videosViewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImageVideos.visibility = View.VISIBLE
                        binding.statusImageVideos.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImageVideos.visibility = View.VISIBLE
                        binding.statusImageVideos.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImageVideos.visibility = View.GONE
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // since the toolbar is initialized in the activity this fragment belongs to,
        // use requireActivity()

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)

        // cast the Activity to an AppCompatActivity.
        // Because the MainActivity class in main_activity.kt inherits AppCompatActivity.
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val title = "Go to movie list"
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = title
        // add the back button to the bar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.goToMovieDetailButton.setOnClickListener {
            // Create the action to navigate to the destination
            val action =
                MovieVideosAndReviewsFragmentDirections.actionMovieVideosAndReviewsToMovieDetailFragment(
                    movie
                )

            // Navigate to the destination using the action
            findNavController().navigate(action)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button.
                // In this case, the back button should be treated as the Up button.
                findNavController().navigate(R.id.action_movieVideosAndReviews_to_MovieListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Unregister the network status callback
        networkStatusCallback.unregisterNetworkCallback()
    }
}