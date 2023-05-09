package com.ltu.m7019e.v23.themoviedb.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

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

@BindingAdapter("reviewAuthorAvatarUrl")
fun bindAuthorAvatarImage(imgView: ImageView, imgUrl: String){
    imgUrl.let{ avatarPath ->
        Glide
            .with(imgView)
            .load(avatarPath)
            .into(imgView);
    }
}

@BindingAdapter("reviewUpdate")
fun bindReviewUpdate(textView: TextView, date: Date){
    date.let{
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        textView.text = formattedDate

    }
}


@BindingAdapter("reviewRating")
fun bindReviewRating(textView: TextView, double: Double){
    double.let{
        val decimalFormat = DecimalFormat("#.#")
        val formattedRating = decimalFormat.format(double)
        textView.text = formattedRating
    }
}