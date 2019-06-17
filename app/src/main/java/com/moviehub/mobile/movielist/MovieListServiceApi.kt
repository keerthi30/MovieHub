package com.moviehub.mobile.movielist

import androidx.lifecycle.LiveData
import com.moviehub.mobile.rest.ApiResponse
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by kreddy on 2019-06-13
 */
interface MovieListServiceApi {

    @GET("/3/movie/now_playing")
    fun getMovieList(): LiveData<ApiResponse<MovieList>>
}