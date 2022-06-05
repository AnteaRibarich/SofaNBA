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
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team
import com.example.sofanba.network.Network
import com.example.sofanba.network.paging.GameByTeamPagingSource
import com.example.sofanba.network.paging.PlayerPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlayerActivityViewModel : ViewModel() {

    var player: MutableLiveData<Player> = MutableLiveData<Player>()
    var team: MutableLiveData<Team> = MutableLiveData<Team>()
    val allFavouritePlayersData = MutableLiveData<List<Player>>()
    lateinit var flowGames: Flow<PagingData<Game>>
    val flowSet: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var playerImage = MutableLiveData<Any>()

    init {
        allFavouritePlayersData.value = listOf()
    }

    val flowPlayers = Pager(PagingConfig(pageSize = 20)) {
        PlayerPagingSource(Network().getService())
    }.flow.cachedIn(viewModelScope)

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

    fun getAllFavouritePlayers(context: Context) {
        viewModelScope.launch {
            val startList =
                NBADatabase.getDatabase(context)?.favouritePlayerDao()?.getAllFavouritePlayers()
                    ?: listOf()
            allFavouritePlayersData.value = startList
        }
    }

    fun getPlayerImages(playerId: Int) {
        viewModelScope.launch {
            val response = Network().getServiceSofa().getAllPlayerImages(playerId)
            println(response)
            if (response.isSuccessful) {
                playerImage.value = response.body()?.data?.get(0)
            } else {
                playerImage.value = playerId
            }
        }
    }

    fun getFlowGamesFromPlayer(team: Team) {
        flowGames = Pager(PagingConfig(pageSize = 20)) {
            GameByTeamPagingSource(Network().getService(), team)
        }.flow.cachedIn(viewModelScope)
        flowSet.value = true
    }
}
