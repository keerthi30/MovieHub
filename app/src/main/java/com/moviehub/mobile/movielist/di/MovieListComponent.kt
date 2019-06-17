package com.moviehub.mobile.movielist.di

import com.moviehub.mobile.movielist.MainActivity
import dagger.Subcomponent


/**
 * Created by kreddy on 2019-06-13
 */
@Subcomponent(modules = [MovieListModule::class])
@MovieListScope
interface MovieListComponent {
    fun inject(activity: MainActivity)
}