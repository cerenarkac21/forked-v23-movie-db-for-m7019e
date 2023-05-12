package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ltu.m7019e.v23.themoviedb.model.Video
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.databinding.MovieVideoListItemBinding

class MovieVideoListAdapter(private val movieVideoClickListener: MovieVideoListClickListener) :
    ListAdapter<Video, MovieVideoListAdapter.ViewHolder>(MovieVideoListDiffCallback()){
    class ViewHolder(private var binding: MovieVideoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video, movieVideoClickListener: MovieVideoListClickListener) {
            binding.video = video
            binding.clickListener = movieVideoClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieVideoListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), movieVideoClickListener)
    }

}

class MovieVideoListDiffCallback: DiffUtil.ItemCallback<Video>(){
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}
class MovieVideoListClickListener(val clickListener: (video: Video) -> Unit) {
    fun onClick(video: Video) = clickListener(video)
}