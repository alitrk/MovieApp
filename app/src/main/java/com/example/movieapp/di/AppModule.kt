package com.example.movieapp.di

import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.repo.MovieRepository
import com.example.movieapp.retrofit.ApiUtils
import com.example.movieapp.retrofit.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(mds: MovieDataSource): MovieRepository{
        return MovieRepository(mds)
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(mdao: MovieDao): MovieDataSource{
        return MovieDataSource(mdao)
    }

    @Provides
    @Singleton
    fun provideKisilerDao() : MovieDao {
        return ApiUtils.getMovieDao()
    }

}