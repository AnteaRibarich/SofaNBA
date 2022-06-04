package com.example.sofanba.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sofanba.model.Game
import com.example.sofanba.model.Team
import com.example.sofanba.network.BallDontLieService

class GameByTeamPagingSource(private val service: BallDontLieService, val team: Team) :
    PagingSource<Int, Game>() {
    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = service.getAllGamesFromTeam(nextPageNumber, 20, team.id).body()
            if (response != null) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = response.meta.next_page
                )
            } else {
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
