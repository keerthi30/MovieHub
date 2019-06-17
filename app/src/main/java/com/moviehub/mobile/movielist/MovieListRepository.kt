package com.moviehub.mobile.movielist

import androidx.lifecycle.LiveData
import com.moviehub.mobile.rest.Resource
import com.moviehub.mobile.rest.ApiResponse
import com.moviehub.mobile.rest.AppExecutors
import com.moviehub.mobile.rest.NetworkBoundResource

/**
 * Created by kreddy on 2019-06-13
 */
class MovieListRepository(
    private val appExecutors: AppExecutors,
    private val dao: MovieListDao,
    private val serviceApi: MovieListServiceApi) {

    var isForceRefresh = false

    fun getMovieList(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MovieList>(appExecutors) {
            override fun saveCallResult(item: MovieList) {
                appExecutors.diskIO().execute {
                    dao.insertMovieList(item.results)
                }

                isForceRefresh = false
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return isForceRefresh || data == null || data.isEmpty()
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