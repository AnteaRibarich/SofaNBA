package com.example.sofanba.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sofanba.model.Player
import com.example.sofanba.network.BallDontLieService

class PlayerPagingSource(private val service: BallDontLieService): PagingSource<Int, Player>() {
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = service.getAllPlayers(nextPageNumber, 20).body()
            if (response != null) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = response.meta.next_page
                )
            }
            else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}