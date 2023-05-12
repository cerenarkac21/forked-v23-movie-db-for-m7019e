package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieVideoResponse {

    // there are 2 parameters returned from the API call

    @Json(name = "id") // the id of the searched movie is returned.
    var id: Long = 0L

    @Json(name = "results")
    var results: List<Video> = listOf()

}