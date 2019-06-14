package com.moviehub.mobile.movielist

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.collection.ArrayMap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.cengage.cendroid.commons.rest.Resource
import com.moviehub.mobile.MovieHubApplication
import com.moviehub.mobile.R
import com.moviehub.mobile.ViewModelFactory
import com.moviehub.mobile.movielist.di.MovieListModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var repository: MovieListRepository

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        MovieHubApplication.appComponent
            .addMovieListComponent(MovieListModule())
            .inject(this)

        //Initialize ViewModel
        val arrayMap = ArrayMap<Class<out ViewModel>, ViewModel>()
        viewModel = MainActivityViewModel(repository)
        arrayMap[MainActivityViewModel::class.java] = viewModel
        val factory = ViewModelFactory(arrayMap)
        ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        viewModel.getMovieList().observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}
