package com.moviehub.mobile

import android.app.Application
import com.moviehub.mobile.di.DaggerMovieHubComponent
import com.moviehub.mobile.di.MovieHubComponent
import com.moviehub.mobile.di.MovieHubModule

/**
 * Created by kreddy on 2019-06-13
 * Copyright © 2018 Cengage Learning, Inc. All rights reserved.
 */
class MovieHubApplication : Application() {

    companion object {
        lateinit var appComponent: MovieHubComponent
    }

    private fun initDagger() {
        appComponent = DaggerMovieHubComponent.builder().movieHubModule(MovieHubModule(this))
            .build()
        appComponent.inject(this)
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }
}