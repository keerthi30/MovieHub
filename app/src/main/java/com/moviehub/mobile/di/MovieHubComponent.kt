package com.moviehub.mobile.di

import com.moviehub.mobile.MovieHubApplication
import com.moviehub.mobile.movielist.di.MovieListComponent
import com.moviehub.mobile.movielist.di.MovieListModule
import dagger.Component
import javax.inject.Singleton


/**
 * Created by kreddy on 2019-06-13
 * Copyright © 2018 Cengage Learning, Inc. All rights reserved.
 */
@Singleton
@Component(modules = [MovieHubModule::class])
interface MovieHubComponent {
    fun inject(application: MovieHubApplication)

    fun addMovieListComponent(module: MovieListModule): MovieListComponent
}