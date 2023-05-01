package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// json will be in a specific format. so, automatically generated adapter will work well for TMDB API
@JsonClass(generateAdapter = true)
class MovieResponse {
    // there are 4 parameters returned from the API call
    @Json(name = "page")
    var page: Int = 0;

    @Json(name = "results")
    var results: List<Movie> = listOf()



    // not will be used these two parameters in this project but it is good to keep them
    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0
}