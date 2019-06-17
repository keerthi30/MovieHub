package com.moviehub.mobile.movielist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.moviehub.mobile.rest.Resource
import com.moviehub.mobile.rest.Status
import com.moviehub.mobile.MovieHubApplication
import com.moviehub.mobile.R
import com.moviehub.mobile.ViewModelFactory
import com.moviehub.mobile.movielist.di.MovieListModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MovieListAdapter.MovieListListener {

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var repository: MovieListRepository

    lateinit var adapter: MovieListAdapter

    private lateinit var movieListLiveData: LiveData<Resource<List<Movie>>>
    private lateinit var movieListObserver: Observer<Resource<List<Movie>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MovieHubApplication.appComponent
            .addMovieListComponent(MovieListModule())
            .inject(this)

        //Initialize ViewModel
        val arrayMap = ArrayMap<Class<out ViewModel>, ViewModel>()
        viewModel = MainActivityViewModel(repository)
        arrayMap[MainActivityViewModel::class.java] = viewModel
        val factory = ViewModelFactory(arrayMap)
        ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        val layoutManager = GridLayoutManager(this, 3)
        recycleView.layoutManager = layoutManager
        adapter = MovieListAdapter(arrayListOf(), this)

        recycleView.adapter = adapter

        movieListObserver = Observer {
            renderUI(it)
        }

        swipeRecycleView.setOnRefreshListener {
            swipeRecycleView.isRefreshing = true
            repository.isForceRefresh = true
            fetchMovieList()
        }

        fetchMovieList()
    }

    private fun fetchMovieList() {
        movieListLiveData = viewModel.getMovieList()
        if (movieListLiveData.hasObservers()) {
            movieListLiveData.removeObserver(movieListObserver)
        }

        movieListLiveData.observe(this, movieListObserver)
    }

    private fun renderUI(resource: Resource<List<Movie>>) {
        resource.data?.let {
            if (it.isNotEmpty()) {
                adapter.updateData(it)
                adapter.notifyDataSetChanged()
                println("$it")
            }
        }

        when (resource.status) {
            Status.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }

            Status.SUCCESS -> {
                swipeRecycleView.isRefreshing = false
                progressBar.visibility = View.GONE
            }

            Status.ERROR -> {
                swipeRecycleView.isRefreshing = false
                progressBar.visibility = View.GONE
                if (adapter.itemCount == 0) {
                    errorTextView.text = "Connection error. Try again later"
                }
            }
        }
    }

    override fun onMovieHoverListener(movie: Movie) {
        Toast.makeText(this, movie.title, Toast.LENGTH_SHORT).show()
    }

}
