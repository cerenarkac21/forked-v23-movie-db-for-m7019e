package com.ltu.m7019e.v23.themoviedb.utils

import android.content.Intent
import android.graphics.Color
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.util.*


@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let { posterPath ->
        Glide
            .with(imgView)
            .load(CONSTANTS.POSTER_IMAGE_BASE_URL + CONSTANTS.POSTER_IMAGE_WIDTH + posterPath)
            .into(imgView)
    }
}

@BindingAdapter("backdropImageUrl")
fun bindBackdropImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let { backdropPath ->
        Glide
            .with(imgView)
            .load(CONSTANTS.BACKDROP_IMAGE_BASE_URL + CONSTANTS.BACKDROP_IMAGE_WIDTH + backdropPath)
            .into(imgView)
    }
}

@BindingAdapter("reviewAuthorAvatarUrl")
fun bindAuthorAvatarImage(imgView: ImageView, imgUrl: String) {
    var avatarPath = imgUrl
    if (imgUrl.startsWith("/https://secure.gravatar.com/avatar")) {
        avatarPath = imgUrl.substring(1)
    } else {
        avatarPath = "https://secure.gravatar.com/avatar$avatarPath"
    }
    Glide.with(imgView).load(avatarPath).into(imgView)
}


@BindingAdapter("reviewRating")
fun bindReviewRating(textView: TextView, double: Double) {
    double.let {
        val decimalFormat = DecimalFormat("#.#")
        val formattedRating = decimalFormat.format(double)
        textView.text = formattedRating
    }
}

@BindingAdapter("movieVideoUrl")
fun bindMovieVideoUrl(webView: WebView, key: String) {
    val movieVideoUrl = "https://www.youtube.com/embed/$key"

    webView.settings.apply {
        javaScriptEnabled = true
    }

    webView.setBackgroundColor(Color.TRANSPARENT)

    webView.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            if (movieVideoUrlIsValid(url)) {
                val html =
                    "<iframe width=\"100%\" height=\"100%\" src=\"$movieVideoUrl\" frameBorder=\"0\" allowFullScreen></iframe>"
                webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            } else {
                // Handle the case when the video is unavailable or deleted
                webView.loadUrl("about:blank") // Clear the WebView content
            }
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)

            // Handle WebView loading errors
            webView.loadUrl("about:blank") // Clear the WebView content
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val intent = Intent(Intent.ACTION_VIEW, request?.url)
            val chooserIntent = Intent.createChooser(intent, "Open with")

            if (intent.resolveActivity(webView.context.packageManager) != null) {
                webView.context.startActivity(chooserIntent)
                return true
            }

            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    webView.loadUrl(movieVideoUrl)
}

private fun movieVideoUrlIsValid(url: String?): Boolean {
    // Perform validation checks on the URL or handle specific error conditions
    // Return true if the video URL is considered valid, false otherwise
    return url != null && !url.startsWith("https://www.youtube.com/error_")
}