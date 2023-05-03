package com.ltu.m7019e.v23.themoviedb

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieVideosAndReviewsBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieVideosAndReviewsViewModel

class MovieVideosAndReviewsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieVideosAndReviewsFragment()
    }

    private var _binding: FragmentMovieVideosAndReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    private lateinit var viewModel: MovieVideosAndReviewsViewModel


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
        _binding = FragmentMovieVideosAndReviewsBinding.inflate(inflater, container, false)
        movie = FragmentMovieVideosAndReviewsArgs.fromBundle(requireArguments()).movie
        binding.movie = movie
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
            val action = MovieVideosAndReviewsFragmentDirections.actionMovieVideosAndReviewsToMovieDetailFragment(movie)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieVideosAndReviewsViewModel::class.java)
        // TODO: Use the ViewModel
    }


}

/*
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <import type="android.view.View" />

        <variable
            name="movie"
            type="com.ltu.m7019e.v23.themoviedb.model.Movie" />

        <variable
            name="viewModel"
            type="com.ltu.m7019e.v23.themoviedb.viewmodel.MovieVideosAndReviewsViewModel" />
    </data>

   contraintlayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"
        tools:context=".MovieVideosAndReviewsFragment">

        <TextView
            android:id="@+id/reviewsTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:text="@string/reviews"
            android:textSize="18sp" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/reviewsRecyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp"
            android:clipToPadding="false"
            android:paddingLeft="16dp"
            android:paddingRight="16dp"
            android:scrollbars="horizontal" bu
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintBottom_toTopOf="@+id/trailersTextView"
            app:layout_constraintTop_toBottomOf="@+id/reviewsTextView"
            app:layout_constraintVertical_bias="0.0"
            app:layout_constraintVertical_chainStyle="packed" />

        <TextView
            android:id="@+id/videosTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:text="@string/videos"
            android:textSize="18sp" />

            contraint layouttta paidding ekle.

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/videosRecyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp"
            android:clipToPadding="false"
            android:paddingLeft="16dp"
            android:paddingRight="16dp"
            android:scrollbars="horizontal"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintBottom_toTopOf="@+id/videosTextView"
            app:layout_constraintTop_toBottomOf="@+id/reviewsTextView"
            app:layout_constraintVertical_bias="0.0"
            app:layout_constraintVertical_chainStyle="packed" />

        <Button
            android:id="@+id/go_to_movie_detail_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_marginBottom="16dp"
            android:text="@string/go_to_movie" />

    </LinearLayout>


</layout>
 */