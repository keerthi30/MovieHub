package com.moviehub.mobile.movielist

import androidx.lifecycle.ViewModel
import com.moviehub.mobile.movielist.MovieListRepository


/**
 * Created by kreddy on 2019-06-14
 */
class MainActivityViewModel(private val repository: MovieListRepository): ViewModel() {

    fun getMovieList() = repository.getMovieList()
}