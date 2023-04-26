package com.example.movieapp.data.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.Movie
import com.example.movieapp.retrofit.MovieDao

class MoviePagingDataSource(private val movieDao: MovieDao, private val title: String) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = movieDao.searchMovie(title, page)
            val movie = response.movieList
            if (response.response == "False") {
                throw java.lang.Exception(response.error)
            }
            LoadResult.Page(
                data = movie,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (movie.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}