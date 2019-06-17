package com.moviehub.mobile.movielist.di

import com.moviehub.mobile.movielist.MovieListDao
import com.moviehub.mobile.movielist.MovieListRepository
import com.moviehub.mobile.movielist.MovieListServiceApi
import com.moviehub.mobile.rest.AppExecutors
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


/**
 * Created by kreddy on 2019-06-13
 */
@Module
class MovieListModule {

    @MovieListScope
    @Provides
    fun provideMovieListServiceApi(retrofit: Retrofit): MovieListServiceApi {
        return retrofit.create(MovieListServiceApi::class.java)
    }

    @MovieListScope
    @Provides
    fun provideMovieListRepository(appExecutors: AppExecutors,
                                   movieListDao: MovieListDao,
                                   serviceApi: MovieListServiceApi): MovieListRepository {
        return MovieListRepository(appExecutors, movieListDao, serviceApi)
    }
}