package com.example.sofanba.ui.explore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofanba.R
import com.example.sofanba.databinding.PlayerListTileBinding
import com.example.sofanba.databinding.TextViewSubtitleBinding
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.model.getFullName

const val STRING_TYPE = 1
const val PLAYER_TYPE = 2
const val TEAM_TYPE = 3

class PlayerPagingAdapter(
    private val context: Context,
    differCallback: DiffUtil.ItemCallback<Any>,
    var isPlayer: Boolean
) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(differCallback) {

    fun setIsPlayer(isPlayer: Boolean) {
        this.isPlayer = isPlayer
    }

    override fun getItemViewType(position: Int): Int {
        // position to type
        return when (position) {
            0 -> STRING_TYPE
            else -> if (isPlayer) PLAYER_TYPE else TEAM_TYPE
        }
    }

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PlayerListTileBinding.bind(itemView)
    }

    class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TextViewSubtitleBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            STRING_TYPE -> {
                // set text
                val stringHolder = holder as StringViewHolder
                stringHolder.binding.root.text =
                    if (isPlayer) context.getString(R.string.all_players) else context.getString(R.string.all_teams)
            }
            PLAYER_TYPE -> {
                // fill player data
                val playerHolder = holder as PlayerViewHolder
                val player = getItem(position) as Player
                playerHolder.binding.textPlayerName.text = player.getFullName()
                playerHolder.binding.textTeam.text = player.team.abbreviation
                playerHolder.binding.imagePlayer.load("")
                playerHolder.binding.imageViewFavourite.setOnClickListener {
                    it.isSelected = !it.isSelected
                }
            }
            TEAM_TYPE -> {
                // fill team data
                val teamHolder = holder as PlayerViewHolder
                val team = getItem(position) as Team
                val teamHelper = TeamHelper.values().firstOrNull { it.name == team.abbreviation }
                teamHolder.binding.textPlayerName.text = team.full_name
                teamHolder.binding.textTeam.visibility = View.GONE
                if (teamHelper != null) {
                    teamHolder.binding.imageTeam.setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            teamHelper.src
                        )
                    )
                    teamHolder.binding.imagePlayer.backgroundTintList =
                        context.getColorStateList(teamHelper.color)
                }
                teamHolder.binding.imageViewFavourite.setOnClickListener {
                    it.isSelected = !it.isSelected
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // view type to viewholder
        return when (viewType) {
            STRING_TYPE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.text_view_subtitle, parent, false)
                StringViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.player_list_tile, parent, false)
                PlayerViewHolder(view)
            }
        }
    }
}
