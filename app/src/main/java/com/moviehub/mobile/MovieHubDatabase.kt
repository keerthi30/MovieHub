package com.moviehub.mobile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moviehub.mobile.movielist.Movie
import com.moviehub.mobile.movielist.MovieListDao

/**
 * Created by kreddy on 2019-06-13
 * Copyright © 2018 Cengage Learning, Inc. All rights reserved.
 */
@Database(entities = [Movie::class], version = 1)
abstract class MovieHubDatabase: RoomDatabase() {
    abstract fun moviesDao(): MovieListDao
}