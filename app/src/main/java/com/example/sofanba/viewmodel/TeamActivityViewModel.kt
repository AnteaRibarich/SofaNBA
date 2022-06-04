package com.example.sofanba.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sofanba.database.NBADatabase
import com.example.sofanba.model.Game
import com.example.sofanba.model.Team
import com.example.sofanba.network.Network
import com.example.sofanba.network.paging.GameByTeamPagingSource
import com.example.sofanba.network.paging.TeamPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeamActivityViewModel : ViewModel() {

    var team: MutableLiveData<Team> = MutableLiveData<Team>()
    val allFavouriteTeamsData = MutableLiveData<List<Team>>()
    lateinit var flowGames: Flow<PagingData<Game>>
    val flowSet: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        allFavouriteTeamsData.value = listOf()
    }

    val flowTeams = Pager(PagingConfig(pageSize = 20)) {
        TeamPagingSource(Network().getService())
    }.flow.cachedIn(viewModelScope)

    fun insertFavouriteTeam(context: Context, team: Team) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouriteTeamDao()?.insertFavouriteTeam(team)
            getAllFavouriteTeams(context)
        }
    }

    fun deleteFavouriteTeam(context: Context, id: Int) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouriteTeamDao()?.deleteFavouriteTeam(id)
            getAllFavouriteTeams(context)
        }
    }

    fun getAllFavouriteTeams(context: Context) {
        viewModelScope.launch {
            val startList =
                NBADatabase.getDatabase(context)?.favouriteTeamDao()?.getAllFavouriteTeams()
                    ?: listOf()
            allFavouriteTeamsData.value = startList
        }
    }

    fun getFlowGamesFromTeam(team: Team) {
        flowGames = Pager(PagingConfig(pageSize = 20)) {
            GameByTeamPagingSource(Network().getService(), team)
        }.flow.cachedIn(viewModelScope)
        flowSet.value = true
    }
}
