package com.example.sofanba.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofanba.database.NBADatabase
import com.example.sofanba.model.Average
import com.example.sofanba.model.Player
import com.example.sofanba.model.SeasonAverage
import com.example.sofanba.model.Team
import com.example.sofanba.network.Network
import kotlinx.coroutines.launch

class PlayerActivityViewModel : ViewModel() {

    var player: MutableLiveData<Player> = MutableLiveData<Player>()
    var team: MutableLiveData<Team> = MutableLiveData<Team>()
    val allFavouritePlayersData = MutableLiveData<List<Player>>()
    var playerImage = MutableLiveData<Any>()
    var seasonAverages: MutableLiveData<HashMap<Int, Average>> = MutableLiveData<HashMap<Int, Average>>()
    private val lastSeasonAvg = 2021
    private val numberOfSeasons = 4

    init {
        allFavouritePlayersData.value = listOf()
        seasonAverages.value = hashMapOf()
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
            if (response.isSuccessful) {
                playerImage.value = response.body()?.data?.get(0)
            } else {
                playerImage.value = playerId
            }
        }
    }

    fun getSeasonAverages(playerId: Int) {
        viewModelScope.launch {
            for (i in 0 until numberOfSeasons) {
                val response =
                    Network().getService().getAllSeasonAverages(lastSeasonAvg - i, playerId)
                if (response.isSuccessful && response.body() is SeasonAverage) {
                    val seasonAverage = response.body() as SeasonAverage

                    if (seasonAverage.data.isNotEmpty()) {
                        val average = seasonAverage.data[0]
                        seasonAverages.value?.set(average.season, average)
                        seasonAverages.postValue(seasonAverages.value)
                    }
                }
            }
        }
    }
}
