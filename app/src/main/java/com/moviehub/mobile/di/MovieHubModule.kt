package com.moviehub.mobile.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moviehub.mobile.BuildConfig
import com.moviehub.mobile.MovieHubDatabase
import com.moviehub.mobile.movielist.MovieListDao
import com.moviehub.mobile.rest.AppExecutors
import com.moviehub.mobile.rest.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by kreddy on 2019-06-13
 */
@Module
class MovieHubModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.context
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): MovieHubDatabase {
        return Room.databaseBuilder(context, MovieHubDatabase::class.java, "db_movie_hub")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(database: MovieHubDatabase): MovieListDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
        return interceptor
    }

    @Provides
    @Singleton
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.MovieDBApiKey)
                .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(url)

            println("Println $url")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun retrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }
}