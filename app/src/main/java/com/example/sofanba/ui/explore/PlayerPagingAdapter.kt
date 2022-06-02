package com.example.sofanba.ui.explore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sofanba.R
import com.example.sofanba.databinding.PlayerListTileBinding
import com.example.sofanba.databinding.TextViewSubtitleBinding
import com.example.sofanba.model.*

const val STRING_TYPE = 1
const val PLAYER_TYPE = 2
const val TEAM_TYPE = 3

class PlayerPagingAdapter(
    private val context: Context,
    differCallback: DiffUtil.ItemCallback<Any>,
    var isPlayer: Boolean,
    private val dataWrapper: DataWrapperHelper,
    private var favourites: List<Any>
) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(differCallback) {

    fun setIsPlayer(isPlayer: Boolean) {
        this.isPlayer = isPlayer
    }

    fun setFavourites(favourites: List<Any>) {
        this.favourites = favourites
        notifyDataSetChanged()
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
                if (getItem(position) is Player) {
                    val player = getItem(position) as Player
                    playerHolder.binding.textPlayerName.text = player.getFullName()
                    playerHolder.binding.textTeam.text = player.team.abbreviation
                    playerHolder.binding.imageViewFavourite.isSelected = favourites.contains(player)
                    playerHolder.binding.imageViewFavourite.setOnClickListener {
                        it.isSelected = !it.isSelected
                        if (it.isSelected) {
                            dataWrapper.insertFavouritePlayer(player)
                        } else {
                            dataWrapper.deleteFavouritePlayer(player)
                        }
                    }
                }
            }
            TEAM_TYPE -> {
                // fill team data
                val teamHolder = holder as PlayerViewHolder
                println(getItem(position))
                if (getItem(position) is Team) {
                    val team = getItem(position) as Team
                    val teamHelper =
                        TeamHelper.values().firstOrNull { it.name == team.abbreviation }
                    teamHolder.binding.textPlayerName.text = team.full_name
                    teamHolder.binding.textTeam.visibility = View.GONE
                    teamHolder.binding.imageViewFavourite.isSelected = favourites.contains(team)
                    teamHolder.binding.imagePlayer.setImageIcon(null)
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
                        if (it.isSelected) {
                            dataWrapper.insertFavouriteTeam(team)
                        } else {
                            dataWrapper.deleteFavouriteTeam(team)
                        }
                    }
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
