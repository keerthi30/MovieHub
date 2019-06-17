package com.moviehub.mobile.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviehub.mobile.R
import com.moviehub.mobile.rest.ApiEndPoints
import kotlinx.android.synthetic.main.adapter_movie_list.view.*


/**
 * Created by kreddy on 2019-06-16
 */
class MovieListAdapter(private var movieList: List<Movie>, private val listener: MovieListListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private lateinit var context: Context

    interface MovieListListener {
        fun onMovieHoverListener(movie: Movie)
    }

    fun updateData(movieList: List<Movie>) {
        this.movieList = movieList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_movie_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {

            val posterPath = ApiEndPoints.ImagePath + movie.poster_path
            Glide
                .with(context)
                .load(posterPath)
                .centerCrop()
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(itemView.movieImageView)


            itemView.setOnHoverListener { v, event ->
                listener.onMovieHoverListener(movie)
                false
            }
        }
    }
}