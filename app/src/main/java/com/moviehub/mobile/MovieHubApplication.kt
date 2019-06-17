package com.moviehub.mobile

import android.app.Application
import com.moviehub.mobile.di.DaggerMovieHubComponent
import com.moviehub.mobile.di.MovieHubComponent
import com.moviehub.mobile.di.MovieHubModule

/**
 * Created by kreddy on 2019-06-13
 */
class MovieHubApplication : Application() {

    companion object {
        lateinit var appComponent: MovieHubComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerMovieHubComponent.builder().movieHubModule(MovieHubModule(this))
            .build()
        appComponent.inject(this)
    }

}