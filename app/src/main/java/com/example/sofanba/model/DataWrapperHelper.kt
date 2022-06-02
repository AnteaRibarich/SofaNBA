package com.example.sofanba.model

import android.content.Context
import com.example.sofanba.viewmodel.MainActivityViewModel

class DataWrapperHelper(val context: Context, private val viewModel: MainActivityViewModel) {
    fun insertFavouritePlayer(player: Player) {
        context.let { viewModel.insertFavouritePlayer(player = player, context = it) }
    }

    fun insertFavouriteTeam(team: Team) {
        context.let { viewModel.insertFavouriteTeam(team = team, context = it) }
    }

    fun deleteFavouritePlayer(player: Player) {
        context.let { viewModel.deleteFavouritePlayer(id = player.id, context = it) }
    }

    fun deleteFavouriteTeam(team: Team) {
        context.let { viewModel.deleteFavouriteTeam(id = team.id, context = it) }
    }
}
