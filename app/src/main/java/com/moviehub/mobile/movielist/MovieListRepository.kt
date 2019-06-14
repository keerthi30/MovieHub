package com.moviehub.mobile.movielist

import androidx.lifecycle.LiveData
import com.cengage.cendroid.commons.rest.Resource
import com.moviehub.mobile.rest.ApiResponse
import com.moviehub.mobile.rest.AppExecutors
import com.moviehub.mobile.rest.NetworkBoundResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by kreddy on 2019-06-13
 * Copyright © 2018 Cengage Learning, Inc. All rights reserved.
 */
class MovieListRepository(
    private val appExecutors: AppExecutors,
    private val dao: MovieListDao,
    private val serviceApi: MovieListServiceApi) {

    fun getMovieList(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MovieList>(appExecutors) {
            override fun saveCallResult(item: MovieList) {
                appExecutors.diskIO().execute {
                    dao.insertMovieList(item.results)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return dao.getMovieList()
            }

            override fun createCall(): LiveData<ApiResponse<MovieList>> {
                return serviceApi.getMovieList()
            }
        }.asLiveData()
    }
}