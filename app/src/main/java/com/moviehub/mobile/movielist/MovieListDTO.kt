package com.moviehub.mobile.movielist

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by kreddy on 2019-06-13
 * Copyright © 2018 Cengage Learning, Inc. All rights reserved.
 */

class MovieList {
    var page = 0
    var results = arrayListOf<Movie>()
    var total_results = 0
    var total_pages = 0
}

@Entity(tableName = "table_movie")
class Movie {

    @PrimaryKey
    @ColumnInfo(name = "column_movie_id")
    var id = 0L

    @ColumnInfo(name = "column_movie_vote_count")
    var vote_count = 0

    @ColumnInfo(name = "column_movie_video")
    var video = false

    @ColumnInfo(name = "column_movie_vote_average")
    var vote_average = 0.0

    @ColumnInfo(name = "column_movie_title")
    var title = ""

    @ColumnInfo(name = "column_movie_popularity")
    var popularity = 0.0

    @ColumnInfo(name = "column_movie_poster_path")
    var poster_path = ""

    @ColumnInfo(name = "column_movie_original_language")
    var original_language = ""

    @TypeConverters(GenreIdListConverter::class)
    @ColumnInfo(name = "column_movie_genre_ids")
    var genre_ids = arrayListOf<Int>()

    @ColumnInfo(name = "column_movie_overview")
    var overview = ""

    @ColumnInfo(name = "column_movie_release_date")
    var release_date = ""
}

class GenreIdListConverter {
    @TypeConverter
    fun fromGenreIdListtToString(studentQuestionList: ArrayList<Int>): String? {
        if (studentQuestionList.isEmpty()) {
            return ""
        }
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().toJson(studentQuestionList, type)
    }

    @TypeConverter
    fun fromGenreIdsToArrayList(studentQuestionListString: String): ArrayList<Int> {
        if (studentQuestionListString.isEmpty()) {
            return arrayListOf()
        }
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson<ArrayList<Int>>(studentQuestionListString, type)
    }
}