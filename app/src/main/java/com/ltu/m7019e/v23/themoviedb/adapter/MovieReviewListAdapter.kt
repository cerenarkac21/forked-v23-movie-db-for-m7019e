package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ltu.m7019e.v23.themoviedb.model.Review
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.databinding.MovieReviewListItemBinding

class MovieReviewListAdapter(private val movieReviewClickListener: MovieReviewListClickListener) :
    ListAdapter<Review, MovieReviewListAdapter.ViewHolder>(MovieReviewListDiffCallback()){
    class ViewHolder(private var binding: MovieReviewListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, movieReviewClickListener: MovieReviewListClickListener) {
            binding.review = review
            binding.clickListener = movieReviewClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieReviewListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), movieReviewClickListener)
    }

}


class MovieReviewListDiffCallback: DiffUtil.ItemCallback<Review>(){
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}
class MovieReviewListClickListener(val clickListener: (review: Review) -> Unit) {
    fun onClick(review: Review) = clickListener(review)
}