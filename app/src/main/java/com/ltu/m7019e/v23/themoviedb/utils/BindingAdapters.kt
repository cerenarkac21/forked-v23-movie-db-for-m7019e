package com.ltu.m7019e.v23.themoviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl:String) {
    imgUrl.let { posterPath ->
        Glide
            .with(imgView)
            .load(SECRETS.POSTER_IMAGE_BASE_URL + SECRETS.POSTER_IMAGE_WIDTH + posterPath)
            .into(imgView);
    }
}

@BindingAdapter("backdropImageUrl")
fun bindBackdropImage(imgView: ImageView, imgUrl:String) {
    imgUrl.let { backdropPath ->
        Glide
            .with(imgView)
            .load(SECRETS.BACKDROP_IMAGE_BASE_URL + SECRETS.BACKDROP_IMAGE_WIDTH + backdropPath)
            .into(imgView);
    }
}