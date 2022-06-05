package com.example.sofanba.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sofanba.database.NBADatabase
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team
import com.example.sofanba.network.Network
import com.example.sofanba.network.paging.GamePagingSource
import com.example.sofanba.network.paging.PlayerPagingSource
import com.example.sofanba.network.paging.TeamPagingSource
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val allFavouritePlayersData = MutableLiveData<List<Player>>()
    val allFavouriteTeamsData = MutableLiveData<List<Team>>()
    val playerImages = MutableLiveData<HashMap<Int, Any>>()

    init {
        allFavouritePlayersData.value = listOf()
        allFavouriteTeamsData.value = listOf()
        playerImages.value = hashMapOf()
    }

    fun getAllFavouritePlayers(context: Context) {
        viewModelScope.launch {
            val startList =
                NBADatabase.getDatabase(context)?.favouritePlayerDao()?.getAllFavouritePlayers()
                    ?: listOf()
            allFavouritePlayersData.value = startList
        }
    }

    fun insertFavouritePlayer(context: Context, player: Player) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouritePlayerDao()?.insertFavouritePlayer(player)
            getAllFavouritePlayers(context)
        }
    }

    fun deleteFavouritePlayer(context: Context, id: Int) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouritePlayerDao()?.deleteFavouritePlayer(id)
            getAllFavouritePlayers(context)
        }
    }

    fun deleteAllFavouritePlayers(context: Context) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouritePlayerDao()?.deleteAllFavouritePlayers()
            getAllFavouritePlayers(context)
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

    fun deleteAllFavouriteTeams(context: Context) {
        viewModelScope.launch {
            NBADatabase.getDatabase(context)?.favouriteTeamDao()?.deleteAllFavouriteTeams()
            getAllFavouriteTeams(context)
        }
    }

    fun getPlayerImages(playerId: Int) {
        viewModelScope.launch {
            val response = Network().getServiceSofa().getAllPlayerImages(playerId)
            if (response.isSuccessful) {
                response.body()?.let { playerImages.value?.set(playerId, it.data) }
            } else {
                playerImages.value?.set(playerId, playerId)
            }
        }
    }

    val flowPlayers = Pager(PagingConfig(pageSize = 20)) {
        PlayerPagingSource(Network().getService())
    }.flow.cachedIn(viewModelScope)

    val flowTeams = Pager(PagingConfig(pageSize = 20)) {
        TeamPagingSource(Network().getService())
    }.flow.cachedIn(viewModelScope)

    val flowGames = Pager(PagingConfig(pageSize = 20)) {
        GamePagingSource(Network().getService())
    }.flow.cachedIn(viewModelScope)
}
