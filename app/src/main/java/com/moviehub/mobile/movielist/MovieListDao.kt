package com.moviehub.mobile.movielist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by kreddy on 2019-06-13
 */
@Dao
interface MovieListDao {

    @Query("SELECT * FROM table_movie")
    fun getMovieList(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList: List<Movie>)
}